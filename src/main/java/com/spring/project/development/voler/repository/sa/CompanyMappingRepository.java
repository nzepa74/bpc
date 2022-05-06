package com.spring.project.development.voler.repository.sa;

import com.spring.project.development.voler.entity.sa.CompanyMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 7/9/2021.
 */
public interface CompanyMappingRepository extends JpaRepository<CompanyMapping, String> {
    void deleteByUserId(String userId);
}
