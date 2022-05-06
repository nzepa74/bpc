package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerSubTarget;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerSubTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerSubTarget_aRepository extends JpaRepository<TfBcpmCusSerSubTarget_a, String> {
    List<TfBcpmCusSerSubTarget_a> findByTargetAuditId(String targetAuditId);
}
