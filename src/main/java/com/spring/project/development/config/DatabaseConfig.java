package com.spring.project.development.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySources({
        @PropertySource("classpath:dbscript/commonDao.mssql.properties"),
        @PropertySource("classpath:dbscript/companyDao.mssql.properties"),
        @PropertySource("classpath:dbscript/userDao.mssql.properties"),
        @PropertySource("classpath:dbscript/permissionDao.mssql.properties"),
        @PropertySource("classpath:dbscript/weightageSetupDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiFinListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiFinEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiCusSerListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiCusSerEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiOrgMgtEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiOrgMgtListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiProdSaleEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfDhiProdSaleListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/compactDocDao.mssql.properties"),
        @PropertySource("classpath:dbscript/notificationDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmFinListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmFinEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmCusSerEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmCusSerListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmOrgMgtEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmOrgMgtListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmProdSaleEditTargetDao.mssql.properties"),
        @PropertySource("classpath:dbscript/tfBcpmProdSaleListDao.mssql.properties"),
        @PropertySource("classpath:dbscript/employeeDao.mssql.properties"),

})
@EnableTransactionManagement
@EnableJpaRepositories("com.spring.project.development")
public class DatabaseConfig {
    private final Environment env;

    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.spring.project.development");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public DataSource dataSource() {
        EncryptDecryptDataSource encryptDecryptDataSource = new EncryptDecryptDataSource();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        // this is for encryption
//        dataSource.setUsername(encryptDecryptDataSource.getDecryptedText(env.getProperty("spring.datasource.username")));
//        dataSource.setPassword(encryptDecryptDataSource.getDecryptedText(env.getProperty("spring.datasource.password")));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;

    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

        return properties;
    }

}
