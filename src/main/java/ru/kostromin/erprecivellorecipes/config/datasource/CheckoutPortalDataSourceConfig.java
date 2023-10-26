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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Конфигурация источника данных для портала выписки. Основной источник данных
 */
@Profile("transferRecipe")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = CheckoutPortalDataSourceConfig.BASE_PACKAGE,
    entityManagerFactoryRef = "checkoutPortalEntityManagerFactory",
    transactionManagerRef = "checkoutPortalTransactionManager"
)
public class CheckoutPortalDataSourceConfig {

  static final String BASE_PACKAGE = "ru.kostromin.erprecivellorecipes.data.checkoutportal";

  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.checkout-portal")
  public DataSourceProperties checkoutPortalDataSourceProperties() {

    return new DataSourceProperties();
  }

  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.checkout-portal.hikari")
  public DataSource checkoutPortalDataSource(
      @Qualifier("checkoutPortalDataSourceProperties") DataSourceProperties dataSourceProperties) {

    return dataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean checkoutPortalEntityManagerFactory(
      @Qualifier("checkoutPortalDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {

    return builder.dataSource(dataSource)
        .packages(CheckoutPortalDataSourceConfig.BASE_PACKAGE)
        .build();
  }

  @Primary
  @Bean
  public PlatformTransactionManager checkoutPortalTransactionManager(
      @Qualifier("checkoutPortalEntityManagerFactory")
      LocalContainerEntityManagerFactoryBean checkoutPortalEntityManagerFactory) {

    return new JpaTransactionManager(Objects.requireNonNull(checkoutPortalEntityManagerFactory.getObject()));
  }
}
