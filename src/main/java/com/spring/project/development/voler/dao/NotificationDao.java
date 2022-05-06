package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.NotificationDto;
 import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created By zepaG on 1/1/2022.
 */
@Repository
public class NotificationDao extends BaseDao {
    private final Environment environment;

    public NotificationDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public NotificationDto getNotificationCount(String userId) {
        String sql = environment.getProperty("NotificationDao.getNotificationCount");
        try {
            return (NotificationDto) entityManager.createNativeQuery(sql)
                    .setParameter("userId", userId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(NotificationDto.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public List<NotificationDto> getNotificationList(String userId) {
        String sqlQuery = environment.getProperty("NotificationDao.getNotificationList");
        List<NotificationDto> notificationDtos = entityManager.createNativeQuery(sqlQuery).unwrap(SQLQuery.class)
                .setParameter("userId", userId).
                        setResultTransformer(Transformers.aliasToBean(NotificationDto.class)).getResultList();
        return notificationDtos;
    }

    @Transactional
    public List<NotificationDto> getAllNotificationList(String userId) {
        String sqlQuery = environment.getProperty("NotificationDao.getAllNotificationList");
        List<NotificationDto> notificationDtos = entityManager.createNativeQuery(sqlQuery).unwrap(SQLQuery.class)
                .setParameter("userId", userId).
                        setResultTransformer(Transformers.aliasToBean(NotificationDto.class)).getResultList();
        return notificationDtos;
    }
}
