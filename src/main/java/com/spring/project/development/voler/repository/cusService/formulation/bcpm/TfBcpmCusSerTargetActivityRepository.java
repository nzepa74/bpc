package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetActivity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerTargetActivityRepository extends JpaRepository<TfBcpmCusSerTargetActivity, String> {
    TfBcpmCusSerTargetActivity findByTargetId(String targetId);
}
