package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleTargetStatusRepository extends JpaRepository<TfBcpmProdSaleTargetStatus, String> {
    TfBcpmProdSaleTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
