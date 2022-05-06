package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetStatus;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerTargetStatusRepository extends JpaRepository<TfBcpmCusSerTargetStatus, String> {
    TfBcpmCusSerTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
