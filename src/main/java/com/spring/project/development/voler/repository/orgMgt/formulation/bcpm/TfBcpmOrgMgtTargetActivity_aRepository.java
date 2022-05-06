package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtTargetActivity_aRepository extends JpaRepository<TfBcpmOrgMgtTargetActivity_a, String> {
    TfBcpmOrgMgtTargetActivity_a findByTargetAuditId(String targetAuditId);
}
