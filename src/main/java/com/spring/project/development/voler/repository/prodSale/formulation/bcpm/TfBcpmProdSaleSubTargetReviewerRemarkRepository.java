package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleSubTargetReviewerRemark;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmProdSaleSubTargetReviewerRemarkRepository extends JpaRepository<TfBcpmProdSaleSubTargetReviewerRemark, String> {
    TfBcpmProdSaleSubTargetReviewerRemark findByRemarkId(String remarkId);

    TfBcpmProdSaleSubTargetReviewerRemark findBySubTargetId(String subTargetId);
}
