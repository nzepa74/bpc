package com.spring.project.development.voler.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by USER on 8/14/2019.
 */
public class FileAttachmentDto {
    //region private variables
    private Integer documentId;
    private String documentTypeId;
    private String applicationNo;
    private String documentType;
    private String documentName;
    private String uploadUrl;
    private String uuId;
    private MultipartFile attachedFile;
    //endregion

    //region setters and getters
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public MultipartFile getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(MultipartFile attachedFile) {
        this.attachedFile = attachedFile;
    }
//endregion
}
