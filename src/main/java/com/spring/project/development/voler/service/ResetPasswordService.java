package com.spring.project.development.voler.service;

import com.spring.project.development.helper.MailSender;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dto.ResetPasswordDto;
import com.spring.project.development.voler.entity.sa.RequestPasswordChange;
import com.spring.project.development.voler.entity.sa.SaUser;
import com.spring.project.development.voler.repository.sa.RequestPasswordChangeRepository;
import com.spring.project.development.voler.repository.sa.SaUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created By zepaG on 4/1/2022.
 */
@Service
public class ResetPasswordService {
    private final RequestPasswordChangeRepository requestPasswordChangeRepository;
    private final SaUserRepository saUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResetPasswordService(RequestPasswordChangeRepository requestPasswordChangeRepository, SaUserRepository saUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.requestPasswordChangeRepository = requestPasswordChangeRepository;
        this.saUserRepository = saUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseMessage requestPasswordChange(ResetPasswordDto resetPasswordDto) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        SaUser saUser = saUserRepository.findByEmail(resetPasswordDto.getEmail());
        if (saUser == null) {
            responseMessage.setText("We couldn't find an account associated with " + resetPasswordDto.getEmail() + ". Please try with different email.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        RequestPasswordChange requestPasswordChange = new RequestPasswordChange();
        requestPasswordChange.setEmail(resetPasswordDto.getEmail());
        requestPasswordChange.setStatus('P');//P =  Requested, C = Changed
        requestPasswordChange.setRequestId(UuidGenerator.generateUuid());
        requestPasswordChange.setCreatedBy(requestPasswordChange.getEmail());
        requestPasswordChange.setCreatedDate(new Date());
        requestPasswordChangeRepository.save(requestPasswordChange);
        sendMail(requestPasswordChange, resetPasswordDto.getDomainName());
        responseMessage.setText("Password reset link sent to your mail. Please check your mail.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    private void sendMail(RequestPasswordChange requestPasswordChange, String domainName) throws Exception {
        String requestId = requestPasswordChange.getRequestId();
        String requestUrl = domainName + "/resetPassword?requestId=" + requestId;
        String toAddress = requestPasswordChange.getEmail();
        String subject = "DHI Group Management System Password Reset";
        String message = "Sir/Madam, <br><br>" +
                "Please click below link to change your password of DHI Group Management System:<br>" +
                "<a href='" + requestUrl + "' target='_blank'>Click here</a><br><br>" +
                "<br><br>Thank you<br>" +
                "Have a good day.<br><br><br>" +
                "<small>****** This is a system generated e-mail. Please do not reply ******</small>";
        MailSender.sendMail(toAddress, null, null, message, subject);
    }

    public ResponseMessage resetPassword(ResetPasswordDto resetPasswordDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        RequestPasswordChange requestPasswordChange = requestPasswordChangeRepository.findByRequestId(resetPasswordDto.getRequestId());
        if (requestPasswordChange != null) {
            String emailDb = requestPasswordChange.getEmail();
            String emailFront = resetPasswordDto.getEmail();
            if (!emailDb.equals(emailFront)) {
                responseMessage.setText("Something went wrong. Please try again");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            } else {
                SaUser saUserDb = saUserRepository.findByUsername(resetPasswordDto.getEmail());
                if (saUserDb == null) {
                    responseMessage.setText("Something went wrong. Please try again");
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    return responseMessage;
                }
                SaUser saUser = new ModelMapper().map(saUserDb, SaUser.class);
                saUser.setUpdatedBy(resetPasswordDto.getEmail());
                saUser.setUpdatedDate(new Date());
                saUser.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
                saUserRepository.save(saUser);

                requestPasswordChange.setStatus('C');//P =  Requested, C = Changed
                requestPasswordChange.setUpdatedBy(saUserDb.getUserId());
                requestPasswordChange.setUpdatedDate(new Date());
                requestPasswordChangeRepository.save(requestPasswordChange);
                responseMessage.setText("Password changed successfully.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            }
        } else {
            responseMessage.setText("Something went wrong. Please try again");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }

}
