package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetWriteup;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtTargetWriteup_aRepository extends JpaRepository<TfDhiOrgMgtTargetWriteup_a, String> {
    TfDhiOrgMgtTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
