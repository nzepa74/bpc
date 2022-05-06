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
public class ResetPasswordDto {
    //region private variables
    private String email;
    private String domainName;
    private String requestId;
    private String password;
    //endregion

}
