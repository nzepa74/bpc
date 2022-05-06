package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetActivity;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetActivity_a;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerTargetActivity_aRepository extends JpaRepository<TfDhiCusSerTargetActivity_a, String> {
    TfDhiCusSerTargetActivity_a findByTargetAuditId(String targetAuditId);
}
