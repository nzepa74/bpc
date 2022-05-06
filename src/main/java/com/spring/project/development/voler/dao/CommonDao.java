package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.helper.DropdownDTO;
import com.spring.project.development.voler.dto.CommentDto;
import com.spring.project.development.voler.dto.EvaluationScoreDto;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Created By zepaG on 8/12/2021.
 */
@Repository
public class CommonDao extends BaseDao {
    private final Environment environment;

    public CommonDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<DropdownDTO> getAllCompanies() {
        String sql = environment.getProperty("CommonDao.getAllCompanies");
        List<DropdownDTO> dropdownDTOS = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).getResultList();
        return dropdownDTOS;
    }

    @Transactional
    public List<DropdownDTO> getMyCompany(String companyId) {
        String sqlQuery = environment.getProperty("CommonDao.getMyCompany");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, DropdownDTO.class)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<DropdownDTO> roleList() {
        String sql = environment.getProperty("CommonDao.roleList");
        List<DropdownDTO> dropdownDTOS = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).getResultList();
        return dropdownDTOS;
    }

    @Transactional
    public List<DropdownDTO> getYearList() {
        String sql = environment.getProperty("CommonDao.getYearList");
        List<DropdownDTO> dropdownDTOS = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).getResultList();
        return dropdownDTOS;
    }

    @Transactional
    public List<CommentDto> getFinComment(String targetId) {
        String sqlQuery = environment.getProperty("CommonDao.getFinComment");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CommentDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<CommentDto> getCusSerComment(String targetId) {
        String sqlQuery = environment.getProperty("CommonDao.getCusSerComment");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CommentDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<CommentDto> getOrgMgtComment(String targetId) {
        String sqlQuery = environment.getProperty("CommonDao.getOrgMgtComment");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CommentDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<CommentDto> getProdSaleComment(String targetId) {
        String sqlQuery = environment.getProperty("CommonDao.getProdSaleComment");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, CommentDto.class)
                .setParameter("targetId", targetId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<EvaluationScoreDto> getEvaluationScore(String year, String companyId) {
        String sqlQuery = environment.getProperty("CommonDao.getEvaluationScore");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, EvaluationScoreDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<String> getReviewerId(String companyId, Integer roleId) {
        String sql = environment.getProperty("CommonDao.getReviewerId");
        NativeQuery nativeQuery = (NativeQuery) hibernateQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("roleId", roleId);
        return (List<String>) nativeQuery.list();
    }

    @Transactional
    public List<String> getCreatorId(String companyId, Integer roleId) {
        String sql = environment.getProperty("CommonDao.getCreatorId");
        NativeQuery nativeQuery = (NativeQuery) hibernateQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("roleId", roleId);
        return (List<String>) nativeQuery.list();
    }

    @Transactional
    public List<EvaluationScoreDto> getAllCompanyScore(String year) {
        String sqlQuery = environment.getProperty("CommonDao.getAllCompanyScore");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, EvaluationScoreDto.class)
                .setParameter("year", year);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public String getMappedCompanyId(String userId, String companyId) {
        String sqlQuery = environment.getProperty("CommonDao.getMappedCompanyId");
        return (String) hibernateQuery(sqlQuery)
                .setParameter("userId", userId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public BigInteger isNewComment(String userId) {
        String sqlQuery = environment.getProperty("CommonDao.isNewComment");
        return (BigInteger) hibernateQuery(sqlQuery)
                .setParameter("userId", userId)
                .uniqueResult();
    }
}
