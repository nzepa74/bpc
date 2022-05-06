package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TfDhiFinTargetRepository extends JpaRepository<TfDhiFinTarget, String> {
    List<TfDhiFinTarget> findByYearAndCompanyId(String year, String companyId);

    TfDhiFinTarget findByTargetId(String targetId);
}
