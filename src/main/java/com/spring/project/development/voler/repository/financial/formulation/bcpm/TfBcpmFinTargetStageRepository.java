package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmFinTargetStageRepository extends JpaRepository<TfBcpmFinTargetStage, String> {
    TfBcpmFinTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
