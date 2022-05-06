package com.spring.project.development.voler.entity.prodSale.formulation.bcpm;

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
@Table(name = "tf_bcpm_prod_sale_target_stage")
public class TfBcpmProdSaleTargetStage extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "stageId", columnDefinition = "varchar(255)")
    private String stageId;

    @Column(name = "year", columnDefinition = "varchar(4)")
    private String year;

    @Column(name = "companyId", columnDefinition = "varchar(255)")
    private String companyId;

    @Column(name = "status", columnDefinition = "char(1)")
    private Character status;

    @Column(name = "roleId", columnDefinition = "int")
    private Integer roleId;

    //endregion
}
