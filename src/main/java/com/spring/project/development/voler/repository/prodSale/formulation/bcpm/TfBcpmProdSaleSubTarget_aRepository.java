package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleSubTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleSubTarget_aRepository extends JpaRepository<TfBcpmProdSaleSubTarget_a, String> {
    List<TfBcpmProdSaleSubTarget_a> findByTargetAuditId(String targetAuditId);
}
