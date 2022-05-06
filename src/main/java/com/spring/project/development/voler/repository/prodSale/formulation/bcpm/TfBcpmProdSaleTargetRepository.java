package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTarget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleTargetRepository extends JpaRepository<TfBcpmProdSaleTarget, String> {
    TfBcpmProdSaleTarget findByTargetId(String targetId);
}
