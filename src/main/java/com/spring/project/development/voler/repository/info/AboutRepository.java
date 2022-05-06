package com.spring.project.development.voler.repository.info;

import com.spring.project.development.voler.entity.info.About;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepository extends JpaRepository<About, String> {
 About findFirstByOrderByAboutIdAsc();
}
