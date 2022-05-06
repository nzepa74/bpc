package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerSubTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerSubTargetRepository extends JpaRepository<TfDhiCusSerSubTarget, String> {
    TfDhiCusSerSubTarget findBySubTargetId(String subTargetId);

    List<TfDhiCusSerSubTarget> findByTargetId(String targetId);
}
