package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleSubTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleSubTarget_aRepository extends JpaRepository<TfDhiProdSaleSubTarget_a, String> {
    List<TfDhiProdSaleSubTarget_a> findByTargetAuditId(String targetAuditId);
}
