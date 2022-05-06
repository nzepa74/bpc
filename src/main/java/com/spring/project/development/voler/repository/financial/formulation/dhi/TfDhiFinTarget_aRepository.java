package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiFinTarget_aRepository extends JpaRepository<TfDhiFinTarget_a, String> {
    TfDhiFinTarget_a findByTargetAuditId(String targetAuditId);
}
