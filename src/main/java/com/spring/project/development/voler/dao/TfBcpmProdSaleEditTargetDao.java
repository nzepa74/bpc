package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.ProdSaleSubTargetDto;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
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
public class TfBcpmProdSaleEditTargetDao extends BaseDao {
    private final Environment environment;

    public TfBcpmProdSaleEditTargetDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public ProdSaleTargetDto getTarget(String targetAuditId) {
        String sql = environment.getProperty("TfBcpmProdSaleEditTargetDao.getTarget");
        try {
            return (ProdSaleTargetDto) entityManager.createNativeQuery(sql)
                    .setParameter("targetAuditId", targetAuditId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(ProdSaleTargetDto.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public List<ProdSaleSubTargetDto> getSubTarget(String targetAuditId) {
        String sqlQuery = environment.getProperty("TfBcpmProdSaleEditTargetDao.getSubTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, ProdSaleSubTargetDto.class)
                .setParameter("targetAuditId", targetAuditId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<EditHistoryDto> getTargetEditHistory(String targetId) {
        String sqlQuery = environment.getProperty("TfBcpmProdSaleEditTargetDao.getTargetEditHistory");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, EditHistoryDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStatusDto> getTargetStatus(String targetId) {
        String sqlQuery = environment.getProperty("TfBcpmProdSaleEditTargetDao.getTargetStatus");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStatusDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public BigInteger getTargetVersionNo(String targetId) {
        String sqlQuery = environment.getProperty("TfBcpmProdSaleEditTargetDao.getTargetVersionNo");
        return (BigInteger) hibernateQuery(sqlQuery).setParameter("targetId", targetId)
                .uniqueResult();
    }
}
