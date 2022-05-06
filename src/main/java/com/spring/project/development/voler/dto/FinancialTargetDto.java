package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class FinancialTargetDto {

    //region private variables
    private String targetId;
    private String targetAuditId;
    private String year;
    private String companyId;
    private Integer serialNo;
    private String finKpi;
    private BigDecimal preYearActual;
    private BigDecimal preYearMidActual;
    private BigDecimal preYearTarget;
    private BigDecimal curYearBudget;
    private BigDecimal curYearTarget;
    private BigDecimal curYearMidTarget;
    private BigDecimal weightage;
    private BigDecimal curYearDhiProposal;
    private String explanation;
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

    private String companyName;
    private List<MultipartFile> attachedFiles;

    //endregion
}
