package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleTargetWriteup_aRepository extends JpaRepository<TfBcpmProdSaleTargetWriteup_a, String> {
    TfBcpmProdSaleTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
