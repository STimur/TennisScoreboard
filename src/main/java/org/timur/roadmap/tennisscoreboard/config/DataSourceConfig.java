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

        // Оптимизация под 1 GiB RAM на сервере
        ds.setMaximumPoolSize(5);      // Максимум 5 одновременных соединений
        ds.setMinimumIdle(1);          // В простое держим только 1 соединение
        ds.setIdleTimeout(30000);      // Закрывать лишние соединения через 30 секунд простоя
        ds.setMaxLifetime(1800000);    // Пересоздавать соединение каждые 30 минут (защита от утечек)

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