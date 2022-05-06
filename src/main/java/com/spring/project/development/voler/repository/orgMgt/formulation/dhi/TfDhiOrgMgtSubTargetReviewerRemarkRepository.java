package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtSubTargetReviewerRemark;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtSubTargetReviewerRemarkRepository extends JpaRepository<TfDhiOrgMgtSubTargetReviewerRemark, String> {
    TfDhiOrgMgtSubTargetReviewerRemark findByRemarkId(String remarkId);

    TfDhiOrgMgtSubTargetReviewerRemark findBySubTargetId(String subTargetId);
}
