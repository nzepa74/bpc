package com.spring.project.development.voler.repository.masterData;

import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightageSetupRepository extends JpaRepository<WeightageSetup, String> {
    WeightageSetup findByWeightageSetupId(String weightageSetupId);

    WeightageSetup findByYearAndCompanyId(String year, String companyId);
}
