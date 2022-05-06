package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.WeightageSetupDto;
import org.hibernate.query.NativeQuery;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class WeightageSetupDao extends BaseDao {
    private final Environment environment;

    public WeightageSetupDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional(readOnly = true)
    public String alreadyExist(String companyId, String year) {
        String sql = environment.getProperty("WeightageSetupDao.alreadyExist");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sql);
        hQuery.setParameter("companyId", companyId).setParameter("year", year);
        return (String) hQuery.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<WeightageSetupDto> getWeightage(String year) {
        String sqlQuery = environment.getProperty("WeightageSetupDao.getWeightage");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, WeightageSetupDto.class)
                .setParameter("year", year);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

}
