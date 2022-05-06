package com.spring.project.development.voler.repository.sa;

import com.spring.project.development.voler.entity.sa.SaUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 7/9/2021.
 */
public interface SaUserRepository extends JpaRepository<SaUser, String> {
    SaUser findByUserId(String userId);

    SaUser findByUsername(String username);

    SaUser findByEmail(String username);
}
