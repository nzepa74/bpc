package com.spring.project.development.voler.entity.prodSale.formulation.dhi;

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
@Table(name = "tf_dhi_prod_sale_sub_target_a")
public class TfDhiProdSaleSubTarget_a extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "subTargetAuditId")
    private String subTargetAuditId;

    @Column(name = "subTargetId")
    private String subTargetId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "targetAuditId")
    private String targetAuditId;

    @Column(name = "subTarget")
    private String subTarget;

    @Column(name = "uom")
    private String uom;

    @Column(name = "preYearActual")
    private BigDecimal preYearActual;

    @Column(name = "preYearTarget")
    private BigDecimal preYearTarget;

    @Column(name = "curYearTarget")
    private BigDecimal curYearTarget;

    @Column(name = "curYearDhiProposal")
    private BigDecimal curYearDhiProposal;

    @Column(name = "weightage")
    private BigDecimal weightage;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
