package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EditHistoryDto {
    //region private variables
    private String targetAuditId;
    private String addedBy;
    private Date addedDate;
    private String addedTime;
    private String editedBy;
    private String editedTime;
    private Date editedDate;
    private Character cmdFlag;
    //endregion
}
