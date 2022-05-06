package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetWriteup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfDhiFinTargetWriteupRepository extends JpaRepository<TfDhiFinTargetWriteup, String> {
    TfDhiFinTargetWriteup findByTargetId(String targetId);
}
