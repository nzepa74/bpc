package com.spring.project.development.voler.repository.sa;

import com.spring.project.development.voler.entity.sa.RequestPasswordChange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 7/9/2021.
 */
public interface RequestPasswordChangeRepository extends JpaRepository<RequestPasswordChange, String> {
    RequestPasswordChange findByRequestId(String requestId);
}
