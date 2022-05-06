package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetActivity;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerTargetActivity_aRepository extends JpaRepository<TfBcpmCusSerTargetActivity_a, String> {
    TfBcpmCusSerTargetActivity_a findByTargetAuditId(String targetAuditId);
}
