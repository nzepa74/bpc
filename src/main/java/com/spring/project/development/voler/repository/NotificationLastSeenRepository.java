package com.spring.project.development.voler.repository;

import com.spring.project.development.voler.entity.NotificationLastSeen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLastSeenRepository extends JpaRepository<NotificationLastSeen, String> {
    void deleteByUserId(String userId);
}
