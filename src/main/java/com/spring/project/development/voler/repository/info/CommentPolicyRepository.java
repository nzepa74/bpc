package com.spring.project.development.voler.repository.info;

import com.spring.project.development.voler.entity.info.CommentPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPolicyRepository extends JpaRepository<CommentPolicy, String> {
 CommentPolicy findFirstByOrderByPolicyIdAsc();
}
