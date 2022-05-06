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
@Table(name = "tf_bcpm_prod_sale_target_status")
public class TfBcpmProdSaleTargetStatus extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "statusId", columnDefinition = "varchar(255)")
    private String statusId;

    @Column(name = "targetId", columnDefinition = "varchar(255)")
    private String targetId;

    @Column(name = "statusFlag", columnDefinition = "char(1)")
    private Character statusFlag;
    //endregion
}
