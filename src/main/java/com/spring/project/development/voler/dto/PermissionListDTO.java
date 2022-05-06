package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PermissionListDTO {
    //region private variables
    private String permissionId;
    private Integer roleId;
    private Integer screenId;
    private Character viewAllowed;
    private Character editAllowed;
    private Character deleteAllowed;
    private Character saveAllowed;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private String screenName;
    //endregion

}
