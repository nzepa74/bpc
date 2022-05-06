package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtSubTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtSubTargetRepository extends JpaRepository<TfDhiOrgMgtSubTarget, String> {
    TfDhiOrgMgtSubTarget findBySubTargetId(String subTargetId);

    List<TfDhiOrgMgtSubTarget> findByTargetId(String targetId);
}
