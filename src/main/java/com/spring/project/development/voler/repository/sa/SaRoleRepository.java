package com.spring.project.development.voler.repository.sa;

import com.spring.project.development.voler.entity.sa.SaRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 7/9/2021.
 */
public interface SaRoleRepository extends JpaRepository<SaRole, String> {

    SaRole findByRoleId(Integer roleId);
}
