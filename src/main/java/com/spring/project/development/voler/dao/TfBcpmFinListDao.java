package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTargetStage;
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
public class TfBcpmFinListDao extends BaseDao {
    private final Environment environment;

    public TfBcpmFinListDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<FinancialTargetDto> searchTarget(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfBcpmFinListDao.searchTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, FinancialTargetDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<FinancialTargetDto> searchByStage(String stageId) {
        String sqlQuery = environment.getProperty("TfBcpmFinListDao.searchByStage");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, FinancialTargetDto.class)
                .setParameter("stageId", stageId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStageDto> getStages(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfBcpmFinListDao.getStages");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStageDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public TfBcpmFinTargetStage getLatestStatus(String year, String companyId) {
        String sql = environment.getProperty("TfBcpmFinListDao.getLatestStatus");
        try {
            return (TfBcpmFinTargetStage) entityManager.createNativeQuery(sql)
                    .setParameter("year", year)
                    .setParameter("companyId", companyId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(TfBcpmFinTargetStage.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
