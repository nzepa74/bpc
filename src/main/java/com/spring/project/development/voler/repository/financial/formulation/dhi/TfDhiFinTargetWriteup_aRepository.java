package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiFinTargetWriteup_aRepository extends JpaRepository<TfDhiFinTargetWriteup_a, String> {
    TfDhiFinTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
