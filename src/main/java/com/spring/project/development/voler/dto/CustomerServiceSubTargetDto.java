package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 10/1/2021.
 */
@Setter
@Getter
public class CustomerServiceSubTargetDto {
    //region private variables
    private String subTargetAuditId;
    private String subTargetId;
    private String targetAuditId;
    private String subTarget;
    private Date deadline;
    private BigDecimal weightage;
    private Boolean isNegativeBoolean;
    private Character isNegative;
    private Character cmdFlag;
    private Character isProratable;
    private Date updatedDate;
    private String updatedBy;
    private String createdBy;
    private Date createdDate;
    private List<MultipartFile> attachedFiles;
    private BigInteger attachmentCount;
    //endregion
}
