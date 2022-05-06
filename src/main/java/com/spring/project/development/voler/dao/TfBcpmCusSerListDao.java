package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.TfBcpmCusSerTargetStage;
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
public class TfBcpmCusSerListDao extends BaseDao {
    private final Environment environment;

    public TfBcpmCusSerListDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<CustomerServiceTargetDto> searchTarget(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfBcpmCusSerListDao.searchTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CustomerServiceTargetDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<CustomerServiceTargetDto> searchByStage(String stageId) {
        String sqlQuery = environment.getProperty("TfBcpmCusSerListDao.searchByStage");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CustomerServiceTargetDto.class)
                .setParameter("stageId", stageId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStageDto> getStages(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfBcpmCusSerListDao.getStages");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStageDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public TfBcpmCusSerTargetStage getLatestStatus(String year, String companyId) {
        String sql = environment.getProperty("TfBcpmCusSerListDao.getLatestStatus");
        try {
            return (TfBcpmCusSerTargetStage) entityManager.createNativeQuery(sql)
                    .setParameter("year", year)
                    .setParameter("companyId", companyId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(TfBcpmCusSerTargetStage.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
