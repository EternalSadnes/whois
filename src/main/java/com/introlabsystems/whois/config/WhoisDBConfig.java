package com.introlabsystems.whois.config;

import com.introlabsystems.whois.config.props.AssessmentTaskDataSourceCfg;
import com.introlabsystems.whois.config.props.WhoisDataSourceCfg;
import com.introlabsystems.whois.model.assessment.AssessmentDomain;
import com.introlabsystems.whois.model.whois.RegistrantInfo;
import com.introlabsystems.whois.repository.assessment.AssessmentDomainRepository;
import com.introlabsystems.whois.repository.whois.RegistrantInfoRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
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
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "whoisEM",
        transactionManagerRef = "whoisTM",
        basePackageClasses = {RegistrantInfoRepository.class})
public class WhoisDBConfig {

    @Bean
    public DataSource whoisDataSource(WhoisDataSourceCfg config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());

        hikariConfig.setMaximumPoolSize(config.getPoolSize());
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("whoisPool");

        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

        return new HikariDataSource(hikariConfig);
    }


    @Bean(name = "whoisEM")
    public LocalContainerEntityManagerFactoryBean whoisEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("whoisDataSource")DataSource dataSource,
            WhoisDataSourceCfg config) {

        return builder.dataSource(dataSource)
                .properties(config.getProperties())
                .packages(RegistrantInfo.class)
                .build();
    }


    @Bean(name = "whoisTM")
    public PlatformTransactionManager whoisTransactionManager(
            @Qualifier("whoisEM")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}