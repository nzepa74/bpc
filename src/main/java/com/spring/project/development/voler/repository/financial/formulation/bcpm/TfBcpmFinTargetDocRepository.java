package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TfBcpmFinTargetDocRepository extends JpaRepository<TfBcpmFinTargetDoc, String> {
    List<TfBcpmFinTargetDoc> findByTargetId(String targetAuditId);

    TfBcpmFinTargetDoc findByFileId(String fileId);
}
