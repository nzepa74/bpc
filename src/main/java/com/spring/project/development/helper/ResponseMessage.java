package com.spring.project.development.helper;

public class ResponseMessage {
    //region private declaration
    private Integer status;
    private String text;
    private String targetAuditId;
    private Object dto;
    private Object writeupDto;
    //endregion

    //region empty constructor
    public ResponseMessage() {
    }
    //endregion

    //region getter and setter

    public String getTargetAuditId() {
        return targetAuditId;
    }

    public void setTargetAuditId(String targetAuditId) {
        this.targetAuditId = targetAuditId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getDTO() {
        return dto;
    }

    public void setDTO(Object dto) {
        this.dto = dto;
    }

    public Object getWriteupDto() {
        return writeupDto;
    }

    public void setWriteupDto(Object writeupDto) {
        this.writeupDto = writeupDto;
    }
    //endregion
}
