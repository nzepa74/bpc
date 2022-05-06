package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivity;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtTargetActivity_aRepository extends JpaRepository<TfDhiOrgMgtTargetActivity_a, String> {
    TfDhiOrgMgtTargetActivity_a findByTargetAuditId(String targetAuditId);
}
