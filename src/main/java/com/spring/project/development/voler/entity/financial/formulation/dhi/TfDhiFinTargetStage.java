package com.spring.project.development.voler.entity.financial.formulation.dhi;

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
@Table(name = "tf_dhi_fin_target_stage")
public class TfDhiFinTargetStage extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "stageId")
    private String stageId;

    @Column(name = "year")
    private String year;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "status")
    private Character status;

    @Column(name = "roleId")
    private Integer roleId;

    //endregion
}
