package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetActivity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtTargetActivityRepository extends JpaRepository<TfBcpmOrgMgtTargetActivity, String> {
    TfBcpmOrgMgtTargetActivity findByTargetId(String targetId);
}
