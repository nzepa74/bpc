package com.spring.project.development.voler.entity.financial.formulation.bcpm;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_bcpm_fin_target_a")
public class TfBcpmFinTarget_a extends BaseEntity {
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

    @Column(name = "finKpi", columnDefinition = "longtext")
    private String finKpi;

    @Column(name = "preYearActual", columnDefinition = "decimal(18,2)")
    private BigDecimal preYearActual;

    @Column(name = "preYearMidActual", columnDefinition = "decimal(18,2)")
    private BigDecimal preYearMidActual;

    @Column(name = "preYearTarget", columnDefinition = "decimal(18,2)")
    private BigDecimal preYearTarget;

    @Column(name = "curYearBudget", columnDefinition = "decimal(18,2)")
    private BigDecimal curYearBudget;

    @Column(name = "curYearTarget", columnDefinition = "decimal(18,2)")
    private BigDecimal curYearTarget;

    @Column(name = "curYearMidTarget", columnDefinition = "decimal(18,2)")
    private BigDecimal curYearMidTarget;

    @Column(name = "weightage", columnDefinition = "decimal(18,2)")
    private BigDecimal weightage;

    @Column(name = "curYearDhiProposal", columnDefinition = "decimal(18,2)")
    private BigDecimal curYearDhiProposal;

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
