package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.CompanyDto;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By zepaG on 8/12/2021.
 */
@Repository
public class CompanyDao extends BaseDao {
    private final Environment environment;

    public CompanyDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public Character getParentCompany() {
        String sql = environment.getProperty("CompanyDao.getParentCompany");
        org.hibernate.query.NativeQuery hQuery = (NativeQuery) hibernateQuery(sql);
        return (Character) hQuery.uniqueResult();
    }

    @Transactional
    public List<CompanyDto> getCompanies() {
        String sql = environment.getProperty("CompanyDao.getCompanies");
        List<CompanyDto> dropdownDTOS = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(CompanyDto.class)).getResultList();
        return dropdownDTOS;
    }

    @Transactional
    public Character getParentCompanyByCompanyId(String companyId) {
        String sql = environment.getProperty("CompanyDao.getParentCompanyByCompanyId");
        org.hibernate.query.NativeQuery hQuery = (NativeQuery) hibernateQuery(sql)
                .setParameter("companyId", companyId);
        return (Character) hQuery.uniqueResult();
    }
}
