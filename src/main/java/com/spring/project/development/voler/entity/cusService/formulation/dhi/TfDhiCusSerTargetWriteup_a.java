package com.spring.project.development.voler.entity.cusService.formulation.dhi;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_dhi_cus_ser_target_writeup_a")
public class TfDhiCusSerTargetWriteup_a extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "writeupAuditId")
    private String writeupAuditId;

    @Column(name = "writeupId")
    private String writeupId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "targetAuditId")
    private String targetAuditId;

    @Column(name = "background")
    private String background;

    @Column(name = "output")
    private String output;

    @Column(name = "risks")
    private String risks;

    @Column(name = "evalMethod")
    private String evalMethod;

    @Column(name = "evalFormulaId")
    private Integer evalFormulaId;

    @Column(name = "isProratable")
    private Character isProratable;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
