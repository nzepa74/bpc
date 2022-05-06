package com.spring.project.development.voler.repository.financial.formulation.dhi;

import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/23/2022.
 */
public interface TfDhiFinTargetStatusRepository extends JpaRepository<TfDhiFinTargetStatus, String> {
    TfDhiFinTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
