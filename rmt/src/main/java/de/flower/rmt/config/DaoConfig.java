package de.flower.rmt.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import de.flower.rmt.repository.factory.RepositoryFactoryBean;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "de.flower.rmt.model",
        "de.flower.common.model",
        //  as ISecurityService is used by the repo layer we include it here
        "de.flower.rmt.security",
        "de.flower.common.validation"
})
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "de.flower.rmt.repository",
        repositoryFactoryBeanClass = RepositoryFactoryBean.class
)
public class DaoConfig {

    @Value("${persistence.unit}")
    private String persistenceUnitName;

    @Value("${jdbc.driver}")
    private String driverClass;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.connection.pool.initialPoolSize}")
    private Integer initialPoolSize;

    @Value("${jdbc.connection.pool.minPoolSize}")
    private Integer minPoolSize;

    @Value("${jdbc.connection.pool.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${jdbc.connection.pool.maxStatements}")
    private Integer maxStatements;

    @Value("${jdbc.connection.pool.maxStatementsPerConnection}")
    private Integer maxStatementsPerConnection;

    @Value("${jdbc.test.period}")
    private Integer idleConnectionTestPeriod;

    @Value("${jdbc.test.query}")
    private String preferredTestQuery;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName(persistenceUnitName);
        emf.setDataSource(dataSource);
        return emf;
    }

    @Bean
    @SneakyThrows
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setMaxStatementsPerConnection(maxStatementsPerConnection);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setPreferredTestQuery(preferredTestQuery);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    /*
     * Validation.
     *
     * Use spring factory to be able to use DI in validator implementations.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return SecurityContextHolder.getContextHolderStrategy();
    }

}
