package com.spring.project.development.voler.repository.info;

import com.spring.project.development.voler.entity.info.TechnicalSupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalSupportRepository extends JpaRepository<TechnicalSupport, String> {
 TechnicalSupport findFirstByOrderByTechnicalSupportIdAsc();
}
