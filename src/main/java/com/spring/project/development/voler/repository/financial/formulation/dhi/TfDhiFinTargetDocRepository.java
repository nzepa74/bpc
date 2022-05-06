package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TfDhiFinTargetDocRepository extends JpaRepository<TfDhiFinTargetDoc, String> {
    List<TfDhiFinTargetDoc> findByTargetId(String targetAuditId);

    TfDhiFinTargetDoc findByFileId(String fileId);
}
