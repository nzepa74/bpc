package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleSubTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleSubTargetRepository extends JpaRepository<TfDhiProdSaleSubTarget, String> {
    TfDhiProdSaleSubTarget findBySubTargetId(String subTargetId);

    List<TfDhiProdSaleSubTarget> findByTargetId(String targetId);
}
