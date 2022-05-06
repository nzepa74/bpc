package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 8/11/2021.
 */
@Setter
@Getter
public class SaUserDto {
    //region private variables
    private String userId;
    private Integer roleId;
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
    private Character status;
    private Character cmdFlag;
    private Date updatedDate;
    private String updatedBy;
    private String createdBy;
    private Date createdDate;
    private String roleName;
    private String oldPassword;
    private String newPassword;
    private String requestId;
    private String mobileNo;
    private String companyId;
    private List<String> companyMappingId;
    private String domainName;
    //endregion

}
