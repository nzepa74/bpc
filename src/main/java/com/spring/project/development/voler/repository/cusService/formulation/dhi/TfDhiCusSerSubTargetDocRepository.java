package com.spring.project.development.voler.repository.cusService.formulation.dhi;

import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerSubTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfDhiCusSerSubTargetDocRepository extends JpaRepository<TfDhiCusSerSubTargetDoc, String> {
    List<TfDhiCusSerSubTargetDoc> findBySubTargetId(String subTargetId);

    TfDhiCusSerSubTargetDoc findByFileId(String fileId);
}
