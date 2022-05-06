package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.SaUserDto;
import com.spring.project.development.voler.entity.masterData.Company;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created By zepaG on 8/12/2021.
 */
@Repository
public class UserDao extends BaseDao {
    private final Environment environment;

    public UserDao(Environment environment) {
        this.environment = environment;
    }


    @Transactional
    public List<SaUserDto> getUsers() {
        String sql = environment.getProperty("UserDao.getUsers");
        List<SaUserDto> saUserDtos = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SaUserDto.class)).getResultList();
        return saUserDtos;
    }

    @Transactional
    public SaUserDto findByUserId(String userId) {
        String sql = environment.getProperty("UserDao.findByUserId");
        try {
            return (SaUserDto) entityManager.createNativeQuery(sql)
                    .setParameter("userId", userId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SaUserDto.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public List<String> getCompanyMappings(String userId) {
        String sql = environment.getProperty("UserDao.getCompanyMappings");
        NativeQuery nativeQuery = (NativeQuery) hibernateQuery(sql)
                .setParameter("userId", userId);
        return (List<String>) nativeQuery.list();
    }

    @Transactional
    public String isEmailAlreadyInUse(String email, String userId) {
        String sqlQuery = environment.getProperty("UserDao.isEmailAlreadyInUse");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery)
                .setParameter("email", email)
                .setParameter("userId", userId);
        return hQuery.list().isEmpty() ? null : (String) hQuery.list().get(0);
    }

    @Transactional
    public String isUsernameAlreadyInUse(String username, String userId) {
        String sqlQuery = environment.getProperty("UserDao.isUsernameAlreadyInUse");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery)
                .setParameter("username", username)
                .setParameter("userId", userId);
        return hQuery.list().isEmpty() ? null : (String) hQuery.list().get(0);
    }

    @Transactional
    public List<Company> getMappedCompanies(String userId) {
        String sqlQuery = environment.getProperty("UserDao.getMappedCompanies");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, Company.class)
                .setParameter("userId", userId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public Company getMyCompany(String userId) {
        String sql = environment.getProperty("UserDao.getMyCompany");
        try {
            return (Company) entityManager.createNativeQuery(sql)
                    .setParameter("userId", userId)
                    .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(Company.class))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
