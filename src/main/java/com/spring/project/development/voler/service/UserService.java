package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.voler.dao.UserDao;
import com.spring.project.development.voler.dto.SaUserDto;
import com.spring.project.development.voler.entity.sa.CompanyMapping;
import com.spring.project.development.voler.entity.sa.SaUser;
import com.spring.project.development.voler.repository.sa.CompanyMappingRepository;
import com.spring.project.development.voler.repository.sa.SaUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final SaUserRepository saUserRepository;
    private final CompanyMappingRepository companyMappingRepository;
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvxyz0123456789";

    public UserService(SaUserRepository saUserRepository, CompanyMappingRepository companyMappingRepository, UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.saUserRepository = saUserRepository;
        this.companyMappingRepository = companyMappingRepository;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseMessage addUser(CurrentUser currentUser, SaUserDto saUserDto) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        String domainName = saUserDto.getDomainName();
        String passwordText = saUserDto.getPassword();
        String confirmPassword = saUserDto.getConfirmPassword();
        if (confirmPassword == null || confirmPassword.equals("")) {
            passwordText = null;
        }
        if (passwordText == null || passwordText.equals("")) {
            passwordText = generatePassword(6);//system generated password
        }
        SaUser saUserDb = saUserRepository.findByEmail(saUserDto.getEmail());
        if (saUserDb != null) {
            if (saUserDto.getUsername().equals(saUserDb.getUsername())) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Email address " + saUserDto.getUsername() + " already in use. Try different one.");
                return responseMessage;
            }
        }
        SaUser saUser = new ModelMapper().map(saUserDto, SaUser.class);
        saUser.setUserId(UuidGenerator.generateUuid());
        saUser.setUsername(saUserDto.getEmail());
        saUser.setPassword(passwordEncoder.encode(passwordText));
        saUser.setCmdFlag(CmdFlag.CREATE.getValue());
        saUser.setCreatedBy(currentUser.getUserId());
        saUser.setCreatedDate(new Date());
        saUserRepository.save(saUser);
        saUserDto.setUserId(saUser.getUserId());
        saveCompanyMapping(saUserDto);
        sendMail(saUser, domainName, passwordText);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional
    public ResponseMessage updateUser(CurrentUser currentUser, SaUserDto saUserDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUserDb = saUserRepository.findByUserId(saUserDto.getUserId());

        String password = saUserDto.getPassword();
        String confirmPassword = saUserDto.getConfirmPassword();
        if (confirmPassword == null || confirmPassword.equals("")) {
            password = null;
        }
        if (password == null || password.equals("")) {
            password = saUserDb.getPassword();
        } else {
            password = passwordEncoder.encode(saUserDto.getPassword());
        }
//        if (saUserDb != null) {
//            if (saUserDto.getUsername().equals(saUserDb.getUsername())) {
//                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
//                responseMessage.setText("Email address already in use. Try different one.");
//                return responseMessage;
//            }
//        }
        String isEmailAlreadyInUse = userDao.isEmailAlreadyInUse(saUserDto.getEmail(), saUserDto.getUserId());
        if (isEmailAlreadyInUse != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Email address " + saUserDto.getUsername() + " already is in use. Try different one.");
            return responseMessage;
        }
        SaUser saUser = new ModelMapper().map(saUserDto, SaUser.class);
        saUser.setUsername(saUserDb.getUsername());
        saUser.setEmail(saUserDto.getEmail());
        saUser.setPassword(password);
        saUser.setCmdFlag(CmdFlag.MODIFY.getValue());
        saUser.setCreatedBy(saUserDb.getUserId());
        saUser.setCreatedDate(saUserDb.getCreatedDate());
        saUser.setUpdatedBy(currentUser.getUserId());
        saUser.setUpdatedDate(new Date());
        saUserRepository.save(saUser);
        companyMappingRepository.deleteByUserId(saUserDto.getUserId());
        saveCompanyMapping(saUserDto);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private void saveCompanyMapping(SaUserDto saUserDto) {
        if (saUserDto.getCompanyMappingId() != null) {
            for (String companyId : saUserDto.getCompanyMappingId()) {
                CompanyMapping companyMapping = new CompanyMapping();
                companyMapping.setCompanyMappingId(UuidGenerator.generateUuid());
                companyMapping.setCompanyId(companyId);
                companyMapping.setUserId(saUserDto.getUserId());
                companyMappingRepository.save(companyMapping);
            }
        }
    }

    public ResponseMessage getUsers() {
        ResponseMessage responseMessage = new ResponseMessage();
        List<SaUserDto> saUserDtos = userDao.getUsers();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (saUserDtos.size() > 0) {
            responseMessage.setDTO(saUserDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getUserByUserId(String userId) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUserDto saUserDto = userDao.findByUserId(userId);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (saUserDto != null) {
            List<String> companyMappings = userDao.getCompanyMappings(saUserDto.getUserId());
            saUserDto.setCompanyMappingId(companyMappings);
            responseMessage.setDTO(saUserDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public static String generatePassword(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private void sendMail(SaUser saUser, String domainName, String passwordText) throws Exception {
        String toAddress = saUser.getEmail();
        String subject = "DHI Group Management System - User added to the system";
        String message = "Sir/Madam, <br><br>" +
                "Your email has been added to DHI Group Management System.<br>" +
                "Please <a href='" + domainName + "' target='_blank'>Click here</a> to login to the system.<br>" +
                "Username/email: " + saUser.getEmail() + "<br>" +
                "Password: " + passwordText + "<br>" +
                "<br><br>Thank you<br>" +
                "Have a good day.<br><br><br>" +
                "<small>****** This is a system generated e-mail. Please do not reply. ******</small>";
        MailSender.sendMail(toAddress, null, null, message, subject);
    }
}
