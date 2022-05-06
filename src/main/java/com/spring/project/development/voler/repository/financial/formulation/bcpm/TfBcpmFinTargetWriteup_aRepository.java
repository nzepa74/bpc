package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmFinTargetWriteup_aRepository extends JpaRepository<TfBcpmFinTargetWriteup_a, String> {
    TfBcpmFinTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
