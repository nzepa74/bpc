package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtTargetStatusRepository extends JpaRepository<TfDhiOrgMgtTargetStatus, String> {
    TfDhiOrgMgtTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
