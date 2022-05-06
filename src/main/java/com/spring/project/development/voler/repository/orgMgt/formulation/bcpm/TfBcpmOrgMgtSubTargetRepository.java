package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtSubTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtSubTargetRepository extends JpaRepository<TfBcpmOrgMgtSubTarget, String> {
    TfBcpmOrgMgtSubTarget findBySubTargetId(String subTargetId);

    List<TfBcpmOrgMgtSubTarget> findByTargetId(String targetId);
}
