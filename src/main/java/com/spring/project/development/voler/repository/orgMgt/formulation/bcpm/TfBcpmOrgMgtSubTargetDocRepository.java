package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtSubTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmOrgMgtSubTargetDocRepository extends JpaRepository<TfBcpmOrgMgtSubTargetDoc, String> {
    List<TfBcpmOrgMgtSubTargetDoc> findBySubTargetId(String subTargetId);

    TfBcpmOrgMgtSubTargetDoc findByFileId(String fileId);
}
