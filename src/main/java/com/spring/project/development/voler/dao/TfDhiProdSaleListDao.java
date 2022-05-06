package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetStage;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTargetStage;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created By zepaG on 3/25/2022.
 */
@Repository
public class TfDhiProdSaleListDao extends BaseDao {
    private final Environment environment;

    public TfDhiProdSaleListDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<ProdSaleTargetDto> searchTarget(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfDhiProdSaleListDao.searchTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, ProdSaleTargetDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<ProdSaleTargetDto> searchSubTarget(String targetAuditId) {
        String sqlQuery = environment.getProperty("TfDhiProdSaleListDao.searchSubTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, ProdSaleTargetDto.class)
                .setParameter("targetAuditId", targetAuditId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<ProdSaleTargetDto> searchByStage(String stageId) {
        String sqlQuery = environment.getProperty("TfDhiProdSaleListDao.searchByStage");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, ProdSaleTargetDto.class)
                .setParameter("stageId", stageId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStageDto> getStages(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfDhiProdSaleListDao.getStages");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStageDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public TfDhiProdSaleTargetStage getLatestStatus(String year, String companyId) {
        String sql = environment.getProperty("TfDhiProdSaleListDao.getLatestStatus");
        try {
            return (TfDhiProdSaleTargetStage) entityManager.createNativeQuery(sql)
                    .setParameter("year", year)
                    .setParameter("companyId", companyId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(TfDhiProdSaleTargetStage.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
