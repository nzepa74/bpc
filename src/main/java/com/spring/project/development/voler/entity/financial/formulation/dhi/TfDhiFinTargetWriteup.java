package com.spring.project.development.voler.entity.financial.formulation.dhi;

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
@Table(name = "tf_dhi_fin_target_writeup")
public class TfDhiFinTargetWriteup extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "writeupId")
    private String writeupId;

    @Column(name = "targetId")
    private String targetId;

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

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
