package com.spring.project.development.voler.repository.prodSale.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetStage;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTargetStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiProdSaleTargetStageRepository extends JpaRepository<TfDhiProdSaleTargetStage, String> {
    TfDhiProdSaleTargetStage findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(String year, String companyId);
}
