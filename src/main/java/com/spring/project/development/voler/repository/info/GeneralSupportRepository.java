package com.spring.project.development.voler.repository.info;

import com.spring.project.development.voler.entity.info.GeneralSupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralSupportRepository extends JpaRepository<GeneralSupport, String> {
 GeneralSupport findFirstByOrderByGeneralSupportIdAsc();
}
