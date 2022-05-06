package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetStage;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetStage;
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
public class TfDhiOrgMgtListDao extends BaseDao {
    private final Environment environment;

    public TfDhiOrgMgtListDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<OrgMgtTargetDto> searchTarget(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfDhiOrgMgtListDao.searchTarget");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, OrgMgtTargetDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<OrgMgtTargetDto> searchByStage(String stageId) {
        String sqlQuery = environment.getProperty("TfDhiOrgMgtListDao.searchByStage");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, OrgMgtTargetDto.class)
                .setParameter("stageId", stageId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<TargetStageDto> getStages(String year, String companyId) {
        String sqlQuery = environment.getProperty("TfDhiOrgMgtListDao.getStages");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, TargetStageDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    public TfDhiOrgMgtTargetStage getLatestStatus(String year, String companyId) {
        String sql = environment.getProperty("TfDhiOrgMgtListDao.getLatestStatus");
        try {
            return (TfDhiOrgMgtTargetStage) entityManager.createNativeQuery(sql)
                    .setParameter("year", year)
                    .setParameter("companyId", companyId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(TfDhiOrgMgtTargetStage.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
