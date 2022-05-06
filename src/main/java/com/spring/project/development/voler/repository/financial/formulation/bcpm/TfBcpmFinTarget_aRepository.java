package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmFinTarget_aRepository extends JpaRepository<TfBcpmFinTarget_a, String> {
    TfBcpmFinTarget_a findByTargetAuditId(String targetAuditId);
}
