package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetReviewerRemark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmFinTargetReviewerRemarkRepository extends JpaRepository<TfBcpmFinTargetReviewerRemark, String> {
    TfBcpmFinTargetReviewerRemark findByTargetId(String targetId);

    TfBcpmFinTargetReviewerRemark findByRemarkId(String remarkId);
}
