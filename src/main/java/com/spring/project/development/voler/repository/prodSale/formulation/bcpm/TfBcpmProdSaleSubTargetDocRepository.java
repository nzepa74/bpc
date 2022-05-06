package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleSubTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleSubTargetDocRepository extends JpaRepository<TfBcpmProdSaleSubTargetDoc, String> {
    List<TfBcpmProdSaleSubTargetDoc> findBySubTargetId(String subTargetId);

    TfBcpmProdSaleSubTargetDoc findByFileId(String fileId);
}
