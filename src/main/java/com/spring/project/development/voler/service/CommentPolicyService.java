package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.entity.info.CommentPolicy;
import com.spring.project.development.voler.entity.info.CommentPolicy;
import com.spring.project.development.voler.repository.info.CommentPolicyRepository;
import com.spring.project.development.voler.repository.info.CommentPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created By zepaG on 11/11/2021.
 */
@Service
public class CommentPolicyService {
    private final CommentPolicyRepository commentPolicyRepository;

    public CommentPolicyService(CommentPolicyRepository commentPolicyRepository) {
        this.commentPolicyRepository = commentPolicyRepository;
    }

    public ResponseMessage addCommentPolicy(CurrentUser currentUser, CommentPolicy commentPolicy) {
        ResponseMessage responseMessage = new ResponseMessage();
        saveCommentPolicy(currentUser, commentPolicy);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Saved successfully.");
        return responseMessage;
    }

    private void saveCommentPolicy(CurrentUser currentUser, CommentPolicy commentPolicy) {
        commentPolicy.setPolicyId(UuidGenerator.generateUuid());
        commentPolicy.setCreatedBy(currentUser.getUserId());
        commentPolicy.setCreatedDate(new Date());
        commentPolicyRepository.deleteAll();//only one row will be saved
        commentPolicyRepository.save(commentPolicy);
    }

    public ResponseMessage editCommentPolicy(CurrentUser currentUser, CommentPolicy commentPolicy) {
        ResponseMessage responseMessage = new ResponseMessage();
        saveCommentPolicy(currentUser, commentPolicy);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage getCommentPolicy() {
        ResponseMessage responseMessage = new ResponseMessage();
         CommentPolicy commentPolicy =  commentPolicyRepository.findFirstByOrderByPolicyIdAsc();
        if (commentPolicy != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(commentPolicy);
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }
}
