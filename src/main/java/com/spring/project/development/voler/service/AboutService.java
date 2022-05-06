package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.entity.info.About;
import com.spring.project.development.voler.repository.info.AboutRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created By zepaG on 11/11/2021.
 */
@Service
public class AboutService {
    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public ResponseMessage addAbout(CurrentUser currentUser, About about) {
        ResponseMessage responseMessage = new ResponseMessage();
        saveAbout(currentUser, about);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Saved successfully.");
        return responseMessage;
    }

    private void saveAbout(CurrentUser currentUser, About about) {
        about.setAboutId(UuidGenerator.generateUuid());
        about.setCreatedBy(currentUser.getUserId());
        about.setCreatedDate(new Date());
        aboutRepository.deleteAll();//only one row will be saved
        aboutRepository.save(about);
    }

    public ResponseMessage editAbout(CurrentUser currentUser, About about) {
        ResponseMessage responseMessage = new ResponseMessage();
        saveAbout(currentUser, about);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }


    public ResponseMessage getAbout() {
        ResponseMessage responseMessage = new ResponseMessage();
         About about =  aboutRepository.findFirstByOrderByAboutIdAsc();
        if (about != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(about);
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }
}
