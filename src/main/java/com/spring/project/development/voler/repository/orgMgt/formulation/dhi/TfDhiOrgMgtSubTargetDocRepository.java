package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtSubTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiOrgMgtSubTargetDocRepository extends JpaRepository<TfDhiOrgMgtSubTargetDoc, String> {
    List<TfDhiOrgMgtSubTargetDoc> findBySubTargetId(String subTargetId);

    TfDhiOrgMgtSubTargetDoc findByFileId(String fileId);
}
