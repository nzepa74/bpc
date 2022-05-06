package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TfBcpmFinTargetRepository extends JpaRepository<TfBcpmFinTarget, String> {
    List<TfBcpmFinTarget> findByYearAndCompanyId(String year, String companyId);

    TfBcpmFinTarget findByTargetId(String targetId);
}
