package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.dto.WriteupDto;
import org.hibernate.query.NativeQuery;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By zepaG on 8/12/2021.
 */
@Repository
public class CompactDocDao extends BaseDao {
    private final Environment environment;

    public CompactDocDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<WriteupDto> getFinWriteupOne(String year, String companyId) {
        String sqlQuery = environment.getProperty("CompactDocDao.getFinWriteupOne");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, WriteupDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<WriteupDto> getCusSerWriteupOne(String year, String companyId) {
        String sqlQuery = environment.getProperty("CompactDocDao.getCusSerWriteupOne");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, WriteupDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<WriteupDto> getOrgMgtWriteupOne(String year, String companyId) {
        String sqlQuery = environment.getProperty("CompactDocDao.getOrgMgtWriteupOne");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, WriteupDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<WriteupDto> getProdSaleWriteupOne(String year, String companyId) {
        String sqlQuery = environment.getProperty("CompactDocDao.getProdSaleWriteupOne");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, WriteupDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<ProdSaleTargetDto> searchProdSaleTargetOne(String year, String companyId) {
        String sqlQuery = environment.getProperty("CompactDocDao.searchProdSaleTargetOne");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, ProdSaleTargetDto.class)
                .setParameter("year", year)
                .setParameter("companyId", companyId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }
}
