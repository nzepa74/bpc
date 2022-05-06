package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.TfBcpmOrgMgtTargetStage;
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
public class TfBcpmOrgMgtListDao extends BaseDao {
    private final Environment environment;

    public TfBcpmOrgMgtListDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<OrgMgtTargetDto> searchTarget(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfBcpmOrgMgtListDao.searchTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, OrgMgtTargetDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<OrgMgtTargetDto> searchByStage(String stageId) {
        String sqlQuery = environment.getProperty("TfBcpmOrgMgtListDao.searchByStage");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, OrgMgtTargetDto.class)
                .setParameter("stageId", stageId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStageDto> getStages(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfBcpmOrgMgtListDao.getStages");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStageDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public TfBcpmOrgMgtTargetStage getLatestStatus(String year, String companyId) {
        String sql = environment.getProperty("TfBcpmOrgMgtListDao.getLatestStatus");
        try {
            return (TfBcpmOrgMgtTargetStage) entityManager.createNativeQuery(sql)
                    .setParameter("year", year)
                    .setParameter("companyId", companyId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(TfBcpmOrgMgtTargetStage.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
