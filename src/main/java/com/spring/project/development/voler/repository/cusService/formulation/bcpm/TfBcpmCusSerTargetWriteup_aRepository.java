package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetWriteup;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetWriteup_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerTargetWriteup_aRepository extends JpaRepository<TfBcpmCusSerTargetWriteup_a, String> {
    TfBcpmCusSerTargetWriteup_a findByTargetAuditId(String targetAuditId);
}
