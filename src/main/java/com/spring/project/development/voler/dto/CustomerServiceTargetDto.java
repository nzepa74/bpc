package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CustomerServiceTargetDto {

    //region private variables
    private String targetId;
    private String targetAuditId;
    private String subTargetAuditId;
    private String subTargetId;
    private String year;
    private String subTarget;
    private String companyId;
    private Integer serialNo;
    private String activity;
    private String explanation;
    private Character cmdFlag;
    private String statusFlag;
    private Character isNegative;

    private String writeupId;
    private String background;
    private String output;
    private String risks;
    private String evalMethod;
    private Integer evalFormulaId;
    private Character isProratable;
    private BigInteger attachmentCount;
    private BigInteger commentCount;
    private BigInteger versionNo;
    private BigDecimal weightage;

    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private Date deadline;

    private String companyName;
    private List<CustomerServiceSubTargetDto> subTargetDtos;
    //endregion
}
