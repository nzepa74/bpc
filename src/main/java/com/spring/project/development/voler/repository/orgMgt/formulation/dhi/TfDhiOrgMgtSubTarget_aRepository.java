package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtSubTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtSubTarget_aRepository extends JpaRepository<TfDhiOrgMgtSubTarget_a, String> {
    List<TfDhiOrgMgtSubTarget_a> findByTargetAuditId(String targetAuditId);
}
