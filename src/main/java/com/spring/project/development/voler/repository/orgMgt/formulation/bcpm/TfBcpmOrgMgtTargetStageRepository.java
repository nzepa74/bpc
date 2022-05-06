package com.spring.project.development.voler.repository.orgMgt.formulation.bcpm;

import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmOrgMgtTargetStageRepository extends JpaRepository<TfBcpmOrgMgtTargetStage, String> {
    TfBcpmOrgMgtTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
