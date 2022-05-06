package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleSubTargetReviewerRemark;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiProdSaleSubTargetReviewerRemarkRepository extends JpaRepository<TfDhiProdSaleSubTargetReviewerRemark, String> {
    TfDhiProdSaleSubTargetReviewerRemark findByRemarkId(String remarkId);

    TfDhiProdSaleSubTargetReviewerRemark findBySubTargetId(String subTargetId);
}
