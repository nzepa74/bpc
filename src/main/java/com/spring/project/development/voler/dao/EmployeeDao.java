package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.helper.DropdownDTO;
import com.spring.project.development.voler.dto.EmployeeDto;
import com.spring.project.development.voler.dto.EvaluationScoreDto;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By zepaG on 5/18/2022.
 */
@Repository
public class EmployeeDao extends BaseDao {

    private final Environment environment;

    public EmployeeDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<EmployeeDto> getAllEmployees() {
        String sql = environment.getProperty("EmployeeDao.getAllEmployees");
        List<EmployeeDto> employeeDtos = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(EmployeeDto.class)).getResultList();
        return employeeDtos;
    }

    @Transactional
    public List<DropdownDTO> getGeogListByDzoId(String dzoId) {
        String sqlQuery = environment.getProperty("EmployeeDao.getGeogListByDzoId");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, DropdownDTO.class)
                .setParameter("dzongId", dzoId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }
}
