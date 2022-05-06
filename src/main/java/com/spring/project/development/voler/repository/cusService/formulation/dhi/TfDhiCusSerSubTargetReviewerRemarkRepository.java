package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerSubTargetReviewerRemark;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerSubTargetReviewerRemarkRepository extends JpaRepository<TfDhiCusSerSubTargetReviewerRemark, String> {
    TfDhiCusSerSubTargetReviewerRemark findByRemarkId(String remarkId);

    TfDhiCusSerSubTargetReviewerRemark findBySubTargetId(String subTargetId);
}
