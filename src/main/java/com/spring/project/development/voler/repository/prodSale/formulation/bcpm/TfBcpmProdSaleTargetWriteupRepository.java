package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTargetWriteup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleTargetWriteupRepository extends JpaRepository<TfBcpmProdSaleTargetWriteup, String> {
    TfBcpmProdSaleTargetWriteup findByTargetId(String targetId);
}
