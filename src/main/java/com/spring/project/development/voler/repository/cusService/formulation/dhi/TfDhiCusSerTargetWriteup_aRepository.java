package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetWriteup;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerTargetWriteup_aRepository extends JpaRepository<TfDhiCusSerTargetWriteup_a, String> {
    TfDhiCusSerTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
