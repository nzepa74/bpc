package com.spring.project.development.voler.repository.masterData;

import com.spring.project.development.voler.entity.masterData.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByCompanyId(String companyId);

    Company findByShortName(String sName);
}
