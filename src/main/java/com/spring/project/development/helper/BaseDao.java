package com.spring.project.development.helper;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional()
public abstract class BaseDao {
    @PersistenceContext
//    protected EntityManager entityManager;
    protected EntityManager entityManager;

//    @Resource(name = "queryProps")
//    protected Properties properties;

    @PersistenceContext(unitName = "default")
    public void setEm(EntityManager em) {
        this.entityManager = em;
    }

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    protected Session getReportingSession() {
        return getCurrentSession();
    }

    protected Session getArchivalSession() {
        return getCurrentSession();
    }

    protected org.hibernate.Query hibernateQuery(String query, Class dtoClazz) {
        return getCurrentSession()
                .createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(dtoClazz));
    }

    protected org.hibernate.Query hibernateReportingQuery(String query, Class dtoClazz) {
        return getReportingSession()
                .createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(dtoClazz));
    }

    protected org.hibernate.Query hibernateArchivalQuery(String query, Class dtoClazz) {
        return getArchivalSession()
                .createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(dtoClazz));
    }

    protected org.hibernate.Query hibernateQuery(String query) {
        return getCurrentSession().createSQLQuery(query);
    }

    protected org.hibernate.Query hibernateReportingQuery(String query) {
        return getReportingSession().createSQLQuery(query);
    }

    protected org.hibernate.Query hibernateArchivalQuery(String query) {
        return getArchivalSession().createSQLQuery(query);
    }

    protected Query persistenceQuery(String query, Class entityClazz) {
        return entityManager.createNativeQuery(query, entityClazz);
    }

    protected Query persistenceQuery(String query) {
        return entityManager.createNativeQuery(query);
    }

}




























