package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerSubTarget;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerSubTarget_a;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerSubTarget_aRepository extends JpaRepository<TfDhiCusSerSubTarget_a, String> {
    List<TfDhiCusSerSubTarget_a> findByTargetAuditId(String targetAuditId);
}
