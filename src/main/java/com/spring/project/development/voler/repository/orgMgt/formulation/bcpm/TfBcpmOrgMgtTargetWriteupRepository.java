package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetWriteup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtTargetWriteupRepository extends JpaRepository<TfBcpmOrgMgtTargetWriteup, String> {
    TfBcpmOrgMgtTargetWriteup findByTargetId(String targetId);
}
