package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetWriteup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TfBcpmFinTargetWriteupRepository extends JpaRepository<TfBcpmFinTargetWriteup, String> {
    TfBcpmFinTargetWriteup findByTargetId(String targetId);
}
