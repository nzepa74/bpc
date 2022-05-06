package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtTargetStatusRepository extends JpaRepository<TfBcpmOrgMgtTargetStatus, String> {
    TfBcpmOrgMgtTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
