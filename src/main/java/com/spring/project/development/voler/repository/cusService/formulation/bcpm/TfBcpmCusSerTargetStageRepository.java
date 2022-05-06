package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetStage;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmCusSerTargetStageRepository extends JpaRepository<TfBcpmCusSerTargetStage, String> {
    TfBcpmCusSerTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
