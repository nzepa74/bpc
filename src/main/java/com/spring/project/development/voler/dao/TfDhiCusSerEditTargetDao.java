package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.CustomerServiceSubTargetDto;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created By zepaG on 3/21/2022.
 */
@Repository
public class TfDhiCusSerEditTargetDao extends BaseDao {
    private final Environment environment;

    public TfDhiCusSerEditTargetDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public CustomerServiceTargetDto getTarget(String targetAuditId) {
        String sql = environment.getProperty("TfDhiCusSerEditTargetDao.getTarget");
        try {
            return (CustomerServiceTargetDto) entityManager.createNativeQuery(sql)
                    .setParameter("targetAuditId", targetAuditId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(CustomerServiceTargetDto.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public List<CustomerServiceSubTargetDto> getSubTarget(String targetAuditId) {
        String sqlQuery = environment.getProperty("TfDhiCusSerEditTargetDao.getSubTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CustomerServiceSubTargetDto.class)
                .setParameter("targetAuditId", targetAuditId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<EditHistoryDto> getTargetEditHistory(String targetId) {
        String sqlQuery = environment.getProperty("TfDhiCusSerEditTargetDao.getTargetEditHistory");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, EditHistoryDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStatusDto> getTargetStatus(String targetId) {
        String sqlQuery = environment.getProperty("TfDhiCusSerEditTargetDao.getTargetStatus");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStatusDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public BigInteger getTargetVersionNo(String targetId) {
        String sqlQuery = environment.getProperty("TfDhiCusSerEditTargetDao.getTargetVersionNo");
        return (BigInteger) hibernateQuery(sqlQuery).setParameter("targetId", targetId)
                .uniqueResult();
    }
}
