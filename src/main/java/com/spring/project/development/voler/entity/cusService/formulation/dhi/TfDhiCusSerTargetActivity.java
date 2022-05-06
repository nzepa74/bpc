package com.spring.project.development.voler.entity.cusService.formulation.dhi;

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
@Table(name = "tf_dhi_cus_ser_target")
public class TfDhiCusSerTargetActivity extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "targetId")
    private String targetId;

    @Column(name = "year")
    private String year;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "serialNo")
    private Integer serialNo;

    @Column(name = "activity")
    private String activity;

    @Column(name = "explanation")
    private String explanation;

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
