package com.spring.project.development.voler.entity.cusService.formulation.dhi;

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
@Table(name = "tf_dhi_cus_ser_sub_target")
public class TfDhiCusSerSubTarget extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "subTargetId")
    private String subTargetId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "subTarget")
    private String subTarget;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "weightage")
    private BigDecimal weightage;

    @Column(name = "isNegative")
    private Character isNegative;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
