package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dto.DocumentDto;
import com.spring.project.development.voler.dto.FileAttachmentDto;
import com.spring.project.development.voler.entity.info.InfoHelpDoc;
import com.spring.project.development.voler.entity.info.GeneralSupport;
import com.spring.project.development.voler.entity.info.TechnicalSupport;
import com.spring.project.development.voler.repository.info.DocumentRepository;
import com.spring.project.development.voler.repository.info.GeneralSupportRepository;
import com.spring.project.development.voler.repository.info.TechnicalSupportRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 11/11/2021.
 */
@Service
public class HelpService {
    private final DocumentRepository documentRepository;
    private final GeneralSupportRepository generalSupportRepository;
    private final TechnicalSupportRepository technicalSupportRepository;

    public HelpService(DocumentRepository documentRepository, GeneralSupportRepository generalSupportRepository, TechnicalSupportRepository technicalSupportRepository) {
        this.documentRepository = documentRepository;
        this.generalSupportRepository = generalSupportRepository;
        this.technicalSupportRepository = technicalSupportRepository;
    }


    public ResponseMessage getFiles() {
        ResponseMessage responseMessage = new ResponseMessage();
        List<InfoHelpDoc> infoHelpDocs = documentRepository.findAll();
        if (infoHelpDocs.size() > 0) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(infoHelpDocs);
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage addFiles(HttpServletRequest request, CurrentUser currentUser, DocumentDto documentDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();

        for (FileAttachmentDto fileAttachmentDTO : documentDto.getFileAttachmentDTOs()) {
            MultipartFile attachedFile = fileAttachmentDTO.getAttachedFile();
            if (attachedFile != null) {
                String attachedFileName = attachedFile.getOriginalFilename();
                String attachedFileExt = attachedFileName.substring(attachedFileName.lastIndexOf(".") + 1);
                FileUploadDTO fileUploadDTO = FileUploadToExternalLocation.fileUploadPathRetriever(request);
                String fileUrl = fileUploadDTO.getUploadFilePath().concat(attachedFileName);
                responseMessage = FileUploadToExternalLocation.fileUploader(attachedFile, attachedFileName, "attachFile.properties", request);

                InfoHelpDoc infoHelpDoc = new InfoHelpDoc();
                infoHelpDoc.setFileId(UuidGenerator.generateUuid());
                infoHelpDoc.setFileUrl(fileUrl);
                infoHelpDoc.setFileName(attachedFileName);
                infoHelpDoc.setFileExtension(attachedFileExt);
                infoHelpDoc.setCreatedBy(currentUser.getUserId());
                infoHelpDoc.setCreatedDate(new Date());
                documentRepository.save(infoHelpDoc);
            }
        }
//        List<MultipartFile> files = documentDto.getFiles();
//        if (files != null) {
//            for (MultipartFile multipartFile : files) {
//                String filename = multipartFile.getOriginalFilename();
//                String fileExtension = Objects.requireNonNull(filename).substring(filename.lastIndexOf(".") + 1, filename.length()).toUpperCase();
//
//                FileUploadDTO fileUploadDTO = FileUploadToExternalLocation.fileUploadPathRetriever(request);
//                String fileUrl = fileUploadDTO.getUploadFilePath().concat(filename);
//                if (!filename.equals("")) {
//                    responseMessage = FileUploadToExternalLocation.fileUploader(multipartFile, filename, "attachFile.properties", request);
//                    if (responseMessage.getStatus() != SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
//                        InfoHelpDoc document = new InfoHelpDoc();
//                        document.setFileId(UuidGenerator.generateUuid());
//                        document.setFileUrl(fileUrl);
//                        document.setFileName(filename);
//                        document.setFileExtension(fileExtension);
//                        document.setCreatedBy(currentUser.getUserId());
//                        document.setCreatedDate(new Date());
//                        documentRepository.save(document);
//                    }
//                }
//            }
//        }
        responseMessage.setText("Saved successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        InfoHelpDoc infoHelpDoc = new InfoHelpDoc();
        infoHelpDoc.setFileId(fileId);
        documentRepository.delete(infoHelpDoc);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        InfoHelpDoc infoHelpDoc = documentRepository.findByFileId(fileId);
        String uploadFilePath = infoHelpDoc.getFileUrl();
        String fileName = infoHelpDoc.getFileName();

        responseMessage = FileUploadToExternalLocation.viewFile(fileName, uploadFilePath, response);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage.setDTO(infoHelpDoc);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;

    }

    public ResponseMessage downloadFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        InfoHelpDoc infoHelpDoc = documentRepository.findByFileId(fileId);
        String uploadFilePath = infoHelpDoc.getFileUrl();
        String fileName = infoHelpDoc.getFileName();

        responseMessage = FileUploadToExternalLocation.fileDownloader(fileName, uploadFilePath, response);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage.setDTO(infoHelpDoc);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }


    public ResponseMessage addGeneralSupport(CurrentUser currentUser, GeneralSupport generalSupport) {
        ResponseMessage responseMessage = new ResponseMessage();
        generalSupport.setGeneralSupportId(UuidGenerator.generateUuid());
        generalSupport.setCreatedBy(currentUser.getUserId());
        generalSupport.setCreatedDate(new Date());
        generalSupportRepository.deleteAll();//only one row will be saved
        generalSupportRepository.save(generalSupport);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");

//        notificationService.saveNotification(currentUser);
        return responseMessage;
    }

    public ResponseMessage getGeneralSupport() {
        ResponseMessage responseMessage = new ResponseMessage();
        GeneralSupport generalSupport = generalSupportRepository.findFirstByOrderByGeneralSupportIdAsc();
        if (generalSupport != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(generalSupport);
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage addTechnicalSupport(CurrentUser currentUser, TechnicalSupport technicalSupport) {
        ResponseMessage responseMessage = new ResponseMessage();
        technicalSupport.setTechnicalSupportId(UuidGenerator.generateUuid());
        technicalSupport.setCreatedBy(currentUser.getUserId());
        technicalSupport.setCreatedDate(new Date());
        technicalSupportRepository.deleteAll();//only one row will be saved
        technicalSupportRepository.save(technicalSupport);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    public ResponseMessage getTechnicalSupport() {
        ResponseMessage responseMessage = new ResponseMessage();
        TechnicalSupport technicalSupport = technicalSupportRepository.findFirstByOrderByTechnicalSupportIdAsc();
        if (technicalSupport != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(technicalSupport);
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }
}
