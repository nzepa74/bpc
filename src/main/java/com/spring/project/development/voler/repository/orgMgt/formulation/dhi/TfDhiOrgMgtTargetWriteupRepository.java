package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetWriteup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtTargetWriteupRepository extends JpaRepository<TfDhiOrgMgtTargetWriteup, String> {
    TfDhiOrgMgtTargetWriteup findByTargetId(String targetId);
}
