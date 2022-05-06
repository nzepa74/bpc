package com.spring.project.development.voler.entity.cusService.formulation.bcpm;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_bcpm_cus_ser_target_a")
public class TfBcpmCusSerTargetActivity_a extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "targetAuditId", columnDefinition = "varchar(255)")
    private String targetAuditId;

    @Column(name = "targetId", columnDefinition = "varchar(255)")
    private String targetId;

    @Column(name = "year", columnDefinition = "varchar(4)")
    private String year;

    @Column(name = "companyId", columnDefinition = "varchar(255)")
    private String companyId;

    @Column(name = "serialNo", columnDefinition = "int")
    private Integer serialNo;

    @Column(name = "activity", columnDefinition = "longtext")
    private String activity;

    @Column(name = "explanation", columnDefinition = "longtext")
    private String explanation;

    @Column(name = "cmdFlag", columnDefinition = "char(1)")
    private Character cmdFlag;

    @Column(name = "versionNo", columnDefinition = "bigint")
    private BigInteger versionNo;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;
    //endregion
}
