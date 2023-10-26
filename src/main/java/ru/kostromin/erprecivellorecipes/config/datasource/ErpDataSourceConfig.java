package ru.kostromin.erprecivellorecipes.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Конфигурация источника данных для e-Rp
 */
@Profile("transferRecipe")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ErpDataSourceConfig.BASE_PACKAGE,
    entityManagerFactoryRef = "erpEntityManagerFactory",
    transactionManagerRef = "erpTransactionManager"
)
public class ErpDataSourceConfig {

  static final String BASE_PACKAGE = "ru.kostromin.erprecivellorecipes.data.erp";

  @Bean
  @ConfigurationProperties("spring.datasource.erp")
  public DataSourceProperties erpDataSourceProperties() {

    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.erp.hikari")
  public DataSource erpDataSource(
      @Qualifier("erpDataSourceProperties") DataSourceProperties dataSourceProperties) {

    return dataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean erpEntityManagerFactory(
      @Qualifier("erpDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {

    return builder.dataSource(dataSource)
        .packages(ErpDataSourceConfig.BASE_PACKAGE)
        .build();
  }

  @Bean
  public PlatformTransactionManager erpTransactionManager(
      @Qualifier("erpEntityManagerFactory")
      LocalContainerEntityManagerFactoryBean erpEntityManagerFactory) {

    return new JpaTransactionManager(Objects.requireNonNull(erpEntityManagerFactory.getObject()));
  }
}
