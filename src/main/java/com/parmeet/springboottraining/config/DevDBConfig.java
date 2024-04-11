package com.parmeet.springboottraining.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile("dev")
@Slf4j
@PropertySource(value = "classpath:db_local.properties", ignoreResourceNotFound = true)
public class DevDBConfig {
    @Bean
    @ConditionalOnProperty(name = "db", havingValue = "hsql", matchIfMissing = true)
    public DataSource hsqlDatasource() {
        return buildAndMigrateDatasource("jdbc:hsqldb:mem:testdb", "sa", "password");
    }

    @Bean
    @ConfigurationProperties("db.local")
    @ConditionalOnProperty(name = "db", havingValue = "local")
    public DataSourceProperties dsProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConditionalOnProperty(name = "db", havingValue = "local")
    public DataSource localDatasource(DataSourceProperties dsProperties) {
        return buildAndMigrateDatasource(dsProperties.getUrl(), dsProperties.getUsername(), dsProperties.getPassword());
    }

    @Bean
    @ConditionalOnProperty(name = "db", havingValue = "mssql")
    public DataSource mssqlDatasource() {
        return buildAndMigrateDatasource("jdbc:sqlserver://localhost:1434;encrypt=false", "sa", "Admin123");
    }

    @SneakyThrows
    private DataSource buildAndMigrateDatasource(String url, String username, String password) {
        var ds = (HikariDataSource) DataSourceBuilder.create().url(url).username(username).password(password).build();
        ds.setMaximumPoolSize(100);
        var conn = ds.getConnection();
        log.info(
            """
                *** INITIALIZING DATASOURCE ***
                    url: {}
                    username: {}
                    schema: {}
                    DB name: {}
                    driver: {}
                    driver version: {}
            """,
                url,
                username,
                conn.getSchema(),
                conn.getMetaData().getDatabaseProductName(),
                conn.getMetaData().getDriverName(),
                conn.getMetaData().getDriverVersion()
        );
        cleanMigrate(ds);
        return ds;
    }

    @SneakyThrows
    private void cleanMigrate(final DataSource ds) {
        var dbType = dbType(ds);
        var flyway = Flyway.configure()
                .dataSource(ds)
                .cleanDisabled(false)
                .baselineOnMigrate(true)
                .sqlMigrationSuffixes(".sql")
                .locations("classpath:db/migrations/"+dbType, "classpath:testdata/")
                .load();
        flyway.clean();
        flyway.migrate();
    }

    private String dbType(DataSource ds) throws SQLException {
        try (var conn = ds.getConnection()) {
            return conn.getMetaData().getURL().contains("sqlserver") ? "mssql" : "hsql";
        }
    }
}
