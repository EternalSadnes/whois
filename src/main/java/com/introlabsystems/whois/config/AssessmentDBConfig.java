package com.introlabsystems.whois.config;

import com.introlabsystems.whois.config.props.AssessmentTaskDataSourceCfg;
import com.introlabsystems.whois.model.assessment.AssessmentDomain;
import com.introlabsystems.whois.repository.assessment.AssessmentDomainRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "assessmentEM",
        transactionManagerRef = "assessmentTM",
        basePackageClasses = {AssessmentDomainRepository.class})
public class AssessmentDBConfig {
    @Primary
    @Bean
    public DataSource dataSource(AssessmentTaskDataSourceCfg config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());

        hikariConfig.setMaximumPoolSize(config.getPoolSize());
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("assessmentPool");

        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

        return new HikariDataSource(hikariConfig);
    }

    @Primary
    @Bean(name = "assessmentEM")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource,
            AssessmentTaskDataSourceCfg config) {

        return builder.dataSource(dataSource)
                .properties(config.getProperties())
                .packages(AssessmentDomain.class)
                .build();
    }

    @Primary
    @Bean(name = "assessmentTM")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
