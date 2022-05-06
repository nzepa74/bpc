package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerSubTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerSubTargetRepository extends JpaRepository<TfBcpmCusSerSubTarget, String> {
    TfBcpmCusSerSubTarget findBySubTargetId(String subTargetId);

    List<TfBcpmCusSerSubTarget> findByTargetId(String targetId);
}
