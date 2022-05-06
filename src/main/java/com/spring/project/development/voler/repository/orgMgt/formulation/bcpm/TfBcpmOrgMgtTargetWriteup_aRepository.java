package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtTargetWriteup_aRepository extends JpaRepository<TfBcpmOrgMgtTargetWriteup_a, String> {
    TfBcpmOrgMgtTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
