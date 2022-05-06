package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtSubTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtSubTarget_aRepository extends JpaRepository<TfBcpmOrgMgtSubTarget_a, String> {
    List<TfBcpmOrgMgtSubTarget_a> findByTargetAuditId(String targetAuditId);
}
