package com.spring.project.development.voler.entity.masterData;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "md_weightage_setup")
public class WeightageSetup extends BaseEntity {

    //region private variables
    @Id
    @Column(name = "weightageSetupId")
    private String weightageSetupId;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "year")
    private String year;

    @Column(name = "financialWt")
    private BigDecimal financialWt;

    @Column(name = "customerWt")
    private BigDecimal customerWt;

    @Column(name = "productionWt")
    private BigDecimal productionWt;

    @Column(name = "orgManagementWt")
    private BigDecimal orgManagementWt;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;
    //endregion

}
