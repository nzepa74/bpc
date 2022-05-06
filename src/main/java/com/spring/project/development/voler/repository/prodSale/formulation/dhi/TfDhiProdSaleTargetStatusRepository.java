package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleTargetStatusRepository extends JpaRepository<TfDhiProdSaleTargetStatus, String> {
    TfDhiProdSaleTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
