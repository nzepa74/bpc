package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class TargetStageDto {
    //region private variables
    private String year;
    private String companyId;
    private List<TargetStageDetailDto> targetStageDetailDtos;
    private String stageId;
    private Character status;
    private String actionTakenBy;
    private Date actionTakenDate;
    private String actionTakenTime;
    //endregion
}
