package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ProdSaleTargetDto {

    //region private variables
    private String targetId;
    private String targetAuditId;
    private String subTargetAuditId;
    private String subTargetId;
    private String year;
    private String subTarget;
    private String companyId;
    private Integer serialNo;
    private String particular;
    private Character cmdFlag;
    private String statusFlag;

    private String writeupId;
    private String background;
    private String output;
    private String risks;
    private String evalMethod;
    private Integer evalFormulaId;
     private BigInteger attachmentCount;
    private BigInteger commentCount;
    private BigInteger versionNo;

    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private Date deadline;

    private String companyName;
    private List<ProdSaleSubTargetDto> subTargetDtos;
    private BigDecimal preYearActual;
    private BigDecimal preYearTarget;
    private BigDecimal curYearTarget;
    private BigDecimal curYearDhiProposal;
    private BigDecimal weightage;
    private String explanation;
    private String uom;
    //endregion
}
