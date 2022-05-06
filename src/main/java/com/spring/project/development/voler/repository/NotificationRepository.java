package com.spring.project.development.voler.repository;

import com.spring.project.development.voler.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 4/11/2022.
 */
public interface NotificationRepository extends JpaRepository<Notification, String> {
}
