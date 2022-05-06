package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.voler.dto.SaUserDto;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By zepaG on 8/12/2021.
 */
@Repository
public class UserListDao extends BaseDao {
    private final Environment environment;

    public UserListDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<SaUserDto> getActiveUsers() {
        String sql = environment.getProperty("UserListDao.getActiveUsers");
        List<SaUserDto> saUserDtos = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SaUserDto.class)).getResultList();
        return saUserDtos;
    }

    @Transactional
    public List<SaUserDto> getInactiveUsers() {
        String sql = environment.getProperty("UserListDao.getInactiveUsers");
        List<SaUserDto> saUserDtos = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SaUserDto.class)).getResultList();
        return saUserDtos;
    }

    @Transactional
    public List<SaUserDto> getAllUsers() {
        String sql = environment.getProperty("UserListDao.getAllUsers");
        List<SaUserDto> saUserDtos = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SaUserDto.class)).getResultList();
        return saUserDtos;
    }

    @Transactional
    public List<SaUserDto> getTrashedUsers() {
        String sql = environment.getProperty("UserListDao.getTrashedUsers");
        List<SaUserDto> saUserDtos = entityManager.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SaUserDto.class)).getResultList();
        return saUserDtos;
    }
}
