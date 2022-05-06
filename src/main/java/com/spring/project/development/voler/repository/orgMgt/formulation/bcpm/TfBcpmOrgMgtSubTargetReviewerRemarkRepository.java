package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtSubTargetReviewerRemark;
import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtSubTargetReviewerRemarkRepository extends JpaRepository<TfBcpmOrgMgtSubTargetReviewerRemark, String> {
    TfBcpmOrgMgtSubTargetReviewerRemark findByRemarkId(String remarkId);

    TfBcpmOrgMgtSubTargetReviewerRemark findBySubTargetId(String subTargetId);
}
