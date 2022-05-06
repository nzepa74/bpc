package com.spring.project.development.voler.dto;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TargetStatusDto extends BaseEntity {
    //region private variables
    private String statusId;
    private String targetId;
    private Character statusFlag;
    private String createdBy;
    private Date createdDate;
    private String createdTime;
    //endregion
}
