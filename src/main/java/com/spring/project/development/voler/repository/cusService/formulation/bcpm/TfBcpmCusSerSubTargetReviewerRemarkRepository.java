package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerSubTargetReviewerRemark;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerSubTargetReviewerRemarkRepository extends JpaRepository<TfBcpmCusSerSubTargetReviewerRemark, String> {
    TfBcpmCusSerSubTargetReviewerRemark findByRemarkId(String remarkId);

    TfBcpmCusSerSubTargetReviewerRemark findBySubTargetId(String subTargetId);
}
