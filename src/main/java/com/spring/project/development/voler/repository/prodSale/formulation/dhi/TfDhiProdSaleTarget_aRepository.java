package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTarget;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleTarget_aRepository extends JpaRepository<TfDhiProdSaleTarget_a, String> {
    TfDhiProdSaleTarget_a findByTargetAuditId(String targetAuditId);
}
