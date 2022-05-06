package com.spring.project.development.voler.dao;

import com.spring.project.development.helper.BaseDao;
import com.spring.project.development.helper.DropdownDTO;
import com.spring.project.development.voler.dto.PermissionListDTO;
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
public class PermissionDao extends BaseDao {
    private final Environment environment;

    public PermissionDao(Environment environment) {
        this.environment = environment;
    }

    @Transactional
    public List<DropdownDTO> getRoles() {
        String sqlQuery = environment.getProperty("PermissionDao.getRoles");
        List<DropdownDTO> roleList = entityManager.createNativeQuery(sqlQuery).unwrap(SQLQuery.class).
                setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).getResultList();
        return roleList;
    }

    @Transactional
    public boolean getIsRoleMapped(Integer roleId) {
        String sqlQuery = environment.getProperty("PermissionDao.getIsRoleMapped");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery).setParameter("roleId", roleId);
         return hQuery.list().size() > 0;
    }

    @Transactional
    public List<PermissionListDTO> getRoleMappedScreens(Integer roleId) {
        String sqlQuery = environment.getProperty("PermissionDao.getRoleMappedScreens");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, PermissionListDTO.class);
        hQuery.setParameter("roleId", roleId);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }

    @Transactional
    public List<PermissionListDTO> getRoleUnmappedScreens() {
        String sqlQuery = environment.getProperty("PermissionDao.getRoleUnmappedScreens");
        NativeQuery hQuery = (NativeQuery) hibernateQuery(sqlQuery, PermissionListDTO.class);
        return hQuery.list().isEmpty() ? null : hQuery.list();
    }
}
