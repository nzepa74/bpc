package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTarget;
import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleTarget_aRepository extends JpaRepository<TfBcpmProdSaleTarget_a, String> {
    TfBcpmProdSaleTarget_a findByTargetAuditId(String targetAuditId);
}
