package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CmdFlag;
import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.voler.dao.UserDao;
import com.spring.project.development.voler.dto.SaUserDto;
import com.spring.project.development.voler.entity.masterData.Company;
import com.spring.project.development.voler.entity.sa.SaUser;
import com.spring.project.development.voler.repository.sa.SaUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 3/23/2022.
 */
@Service
public class MyProfileService {
    private final SaUserRepository saUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public MyProfileService(SaUserRepository saUserRepository, BCryptPasswordEncoder passwordEncoder, UserDao userDao) {
        this.saUserRepository = saUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public ResponseMessage changePassword(CurrentUser currentUser, SaUserDto saUserDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUserDb = saUserRepository.findByUserId(currentUser.getUserId());
        String newPassword = saUserDto.getNewPassword();
        String currentPassword = saUserDto.getOldPassword();
        String oldPassword = saUserDb.getPassword();

        if (passwordEncoder.matches(currentPassword, oldPassword)) {
            SaUser saUser = new ModelMapper().map(saUserDb, SaUser.class);
            saUser.setPassword(passwordEncoder.encode(newPassword));
            saUser.setCmdFlag(CmdFlag.MODIFY.getValue());
            saUser.setUpdatedBy(currentUser.getUserId());
            saUser.setUpdatedDate(new Date());
            saUser.setCreatedBy(saUserDb.getUserId());
            saUser.setCreatedDate(saUserDb.getCreatedDate());
            saUserRepository.save(saUser);
            responseMessage.setText("Password changed successfully.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            return responseMessage;
        } else {
            responseMessage.setText("Current password doesn't match in the record.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
    }

    public ResponseMessage getMyDetail(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        SaUser saUser = saUserRepository.findByUserId(currentUser.getUserId());
        if (saUser != null) {
            responseMessage.setDTO(saUser);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    public ResponseMessage editFullName(CurrentUser currentUser, SaUserDto saUserDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUserDb = saUserRepository.findByUserId(currentUser.getUserId());
        SaUser saUser = new ModelMapper().map(saUserDb, SaUser.class);
        saUser.setFullName(saUserDto.getFullName());
        saUser.setCmdFlag(CmdFlag.MODIFY.getValue());
        saUser.setUpdatedBy(currentUser.getUserId());
        saUser.setUpdatedDate(new Date());
        saUserRepository.save(saUser);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage editUsername(CurrentUser currentUser, SaUserDto saUserDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUserDb = saUserRepository.findByUserId(currentUser.getUserId());
        String username = saUserDto.getUsername().trim();
        if (username.contains(" ")) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Username must not contain white space in between.");
            return responseMessage;
        }
        String isEmailAlreadyInUse = userDao.isUsernameAlreadyInUse(saUserDto.getUsername(), currentUser.getUserId());
        if (isEmailAlreadyInUse != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Username " + saUserDto.getUsername() + " already is in use. Try different one.");
            return responseMessage;
        }
        SaUser saUser = new ModelMapper().map(saUserDb, SaUser.class);
        saUser.setUsername(saUserDto.getUsername());
        saUser.setCmdFlag(CmdFlag.MODIFY.getValue());
        saUser.setUpdatedBy(currentUser.getUserId());
        saUser.setUpdatedDate(new Date());
        saUserRepository.save(saUser);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage editEmail(CurrentUser currentUser, SaUserDto saUserDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUserDb = saUserRepository.findByUserId(currentUser.getUserId());
        String isEmailAlreadyInUse = userDao.isEmailAlreadyInUse(saUserDto.getEmail(), currentUser.getUserId());
        if (isEmailAlreadyInUse != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Email address " + saUserDto.getEmail() + " already is in use. Try different one.");
            return responseMessage;
        }

        SaUser saUser = new ModelMapper().map(saUserDb, SaUser.class);
        saUser.setEmail(saUserDto.getEmail());
        saUser.setCmdFlag(CmdFlag.MODIFY.getValue());
        saUser.setUpdatedBy(currentUser.getUserId());
        saUser.setUpdatedDate(new Date());
        saUserRepository.save(saUser);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage editMobileNo(CurrentUser currentUser, SaUserDto saUserDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUserDb = saUserRepository.findByUserId(currentUser.getUserId());
        SaUser saUser = new ModelMapper().map(saUserDb, SaUser.class);
        saUser.setMobileNo(saUserDto.getMobileNo());
        saUser.setCmdFlag(CmdFlag.MODIFY.getValue());
        saUser.setUpdatedBy(currentUser.getUserId());
        saUser.setUpdatedDate(new Date());
        saUserRepository.save(saUser);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage getMappedCompanies(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<Company> companies = userDao.getMappedCompanies(currentUser.getUserId());
        if (companies != null) {
            responseMessage.setDTO(companies);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getMyCompany(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        Company company = userDao.getMyCompany(currentUser.getUserId());
        if (company != null) {
            responseMessage.setDTO(company);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
