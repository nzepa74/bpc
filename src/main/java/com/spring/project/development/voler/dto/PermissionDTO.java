package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PermissionDTO {

    //region private variables
    private Integer roleId;

    private List<PermissionListDTO> permissionListDTOS;
    //endregion

}
