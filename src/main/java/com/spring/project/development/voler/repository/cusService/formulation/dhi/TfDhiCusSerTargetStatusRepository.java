package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetStatus;
import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerTargetStatusRepository extends JpaRepository<TfDhiCusSerTargetStatus, String> {
    TfDhiCusSerTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
