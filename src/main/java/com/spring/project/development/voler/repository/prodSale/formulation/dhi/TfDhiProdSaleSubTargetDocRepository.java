package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleSubTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleSubTargetDocRepository extends JpaRepository<TfDhiProdSaleSubTargetDoc, String> {
    List<TfDhiProdSaleSubTargetDoc> findBySubTargetId(String subTargetId);

    TfDhiProdSaleSubTargetDoc findByFileId(String fileId);
}
