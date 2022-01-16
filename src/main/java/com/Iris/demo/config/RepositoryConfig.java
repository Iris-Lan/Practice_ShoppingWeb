package com.Iris.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.Iris.demo.repository"},
        entityManagerFactoryRef = "rdbEntityManagerFactory",
        transactionManagerRef = "rdbTransactionManager")
public class RepositoryConfig {
    @Autowired
    private Environment env;

    @PostConstruct
    public void init(){
        log.debug("== DB URL: " + env.getProperty("spring.datasource.url") + " ==");
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties(){
        return new JpaProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("rdbDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "rdbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean rdbEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                          @Qualifier("rdbDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
                .dataSource(dataSource)
                .packages("com.Iris.demo.entity")
                .persistenceUnit("primaryPersistenceUnit")
                .build();

        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(hibernateProperties());
        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "rdbTransactionManager")
    public PlatformTransactionManager rdbTransactionManager(
            EntityManagerFactoryBuilder builder, @Qualifier("rdbDataSource") DataSource dataSource) {
        return new JpaTransactionManager(Objects.requireNonNull(rdbEntityManagerFactory(builder, dataSource).getObject()));
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", jpaProperties().getProperties().get(("hibernate.dialect")));
        properties.setProperty("hibernate.show_sql", String.valueOf(jpaProperties().isShowSql()));
        properties.setProperty("hibernate.hbm2ddl.auto", jpaProperties().getProperties().get(("hibernate.ddl-auto")));
        properties.setProperty("hibernate.format_sql", jpaProperties().getProperties().get(("hibernate.formatSql")));
        return properties;
    }
}
