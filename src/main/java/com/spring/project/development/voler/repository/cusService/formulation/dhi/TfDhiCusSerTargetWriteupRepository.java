package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetWriteup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerTargetWriteupRepository extends JpaRepository<TfDhiCusSerTargetWriteup, String> {
    TfDhiCusSerTargetWriteup findByTargetId(String targetId);
}
