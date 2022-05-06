package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtTargetActivityRepository extends JpaRepository<TfDhiOrgMgtTargetActivity, String> {
    TfDhiOrgMgtTargetActivity findByTargetId(String targetId);
}
