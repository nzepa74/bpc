package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetStage;
import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiCusSerTargetStageRepository extends JpaRepository<TfDhiCusSerTargetStage, String> {
    TfDhiCusSerTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
