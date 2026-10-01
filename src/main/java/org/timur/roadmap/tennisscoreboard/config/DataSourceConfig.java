package org.timur.roadmap.tennisscoreboard.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(requiredEnv("DB_URL"));
        ds.setUsername(requiredEnv("DB_USERNAME"));
        ds.setPassword(requiredEnv("DB_PASSWORD"));
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setMaximumPoolSize(10);
        return ds;
    }

    private String requiredEnv(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required environment variable is not set: " + name
            );
        }

        return value;
    }
}