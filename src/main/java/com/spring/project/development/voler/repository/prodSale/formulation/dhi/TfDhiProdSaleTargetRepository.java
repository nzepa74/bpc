package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTarget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleTargetRepository extends JpaRepository<TfDhiProdSaleTarget, String> {
    TfDhiProdSaleTarget findByTargetId(String targetId);
}
