package com.spring.project.development.voler.entity.prodSale.formulation.dhi;

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
@Table(name = "tf_dhi_prod_sale_target_a")
public class TfDhiProdSaleTarget_a extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "targetAuditId")
    private String targetAuditId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "year")
    private String year;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "serialNo")
    private Integer serialNo;

    @Column(name = "particular")
    private String particular;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "versionNo")
    private BigInteger versionNo;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
