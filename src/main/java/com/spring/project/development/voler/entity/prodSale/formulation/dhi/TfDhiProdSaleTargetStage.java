package com.spring.project.development.voler.entity.prodSale.formulation.dhi;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_dhi_prod_sale_target_stage")
public class TfDhiProdSaleTargetStage extends BaseEntity {
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
