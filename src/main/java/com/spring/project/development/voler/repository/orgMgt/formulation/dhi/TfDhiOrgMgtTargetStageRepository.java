package com.spring.project.development.voler.repository.orgMgt.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetStage;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiOrgMgtTargetStageRepository extends JpaRepository<TfDhiOrgMgtTargetStage, String> {
    TfDhiOrgMgtTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
