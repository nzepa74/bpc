package com.spring.project.development.voler.repository.financial.formulation.bcpm;

import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 3/23/2022.
 */
public interface TfBcpmFinTargetStatusRepository extends JpaRepository<TfBcpmFinTargetStatus, String> {
    TfBcpmFinTargetStatus findTop1ByTargetIdOrderByCreatedDateDesc(String targetId);
}
