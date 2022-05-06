package com.spring.project.development.voler.repository;

import com.spring.project.development.voler.entity.NotifyTo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 4/11/2022.
 */
public interface NotifyToRepository extends JpaRepository<NotifyTo, String> {
    NotifyTo findByNotifyToId(String notifyToId);
}
