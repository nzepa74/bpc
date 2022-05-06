package com.spring.project.development.voler.repository.prodSale.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetStage;
import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmProdSaleTargetStageRepository extends JpaRepository<TfBcpmProdSaleTargetStage, String> {
    TfBcpmProdSaleTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
