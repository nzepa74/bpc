package com.spring.project.development.voler.entity.prodSale.formulation.bcpm;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_bcpm_prod_sale_sub_target_a")
public class TfBcpmProdSaleSubTarget_a extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "subTargetAuditId", columnDefinition = "varchar(255)")
    private String subTargetAuditId;

    @Column(name = "subTargetId", columnDefinition = "varchar(255)")
    private String subTargetId;

    @Column(name = "targetId", columnDefinition = "varchar(255)")
    private String targetId;

    @Column(name = "targetAuditId", columnDefinition = "varchar(255)")
    private String targetAuditId;

    @Column(name = "subTarget", columnDefinition = "longtext")
    private String subTarget;

    @Column(name = "uom", columnDefinition = "varchar(255)")
    private String uom;

    @Column(name = "preYearActual", columnDefinition = "decimal(18,2)")
    private BigDecimal preYearActual;

    @Column(name = "preYearTarget", columnDefinition = "decimal(18,2)")
    private BigDecimal preYearTarget;

    @Column(name = "curYearTarget", columnDefinition = "decimal(18,2)")
    private BigDecimal curYearTarget;

    @Column(name = "curYearDhiProposal", columnDefinition = "decimal(18,2)")
    private BigDecimal curYearDhiProposal;

    @Column(name = "weightage", columnDefinition = "decimal(18,2)")
    private BigDecimal weightage;

    @Column(name = "explanation", columnDefinition = "longtext")
    private String explanation;

    @Column(name = "cmdFlag", columnDefinition = "char(1)")
    private Character cmdFlag;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;
    //endregion
}
