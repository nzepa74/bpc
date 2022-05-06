package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetReviewerRemark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiFinTargetReviewerRemarkRepository extends JpaRepository<TfDhiFinTargetReviewerRemark, String> {
    TfDhiFinTargetReviewerRemark findByTargetId(String targetId);

    TfDhiFinTargetReviewerRemark findByRemarkId(String remarkId);
}
