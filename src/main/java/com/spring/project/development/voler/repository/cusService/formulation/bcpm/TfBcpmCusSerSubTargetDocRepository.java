package com.spring.project.development.voler.repository.cusService.formulation.bcpm;

import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerSubTargetDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By zepaG on 3/26/2022.
 */
public interface TfBcpmCusSerSubTargetDocRepository extends JpaRepository<TfBcpmCusSerSubTargetDoc, String> {
    List<TfBcpmCusSerSubTargetDoc> findBySubTargetId(String subTargetId);

    TfBcpmCusSerSubTargetDoc findByFileId(String fileId);
}
