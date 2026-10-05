package org.timur.roadmap.tennisscoreboard.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Комментарии, которые просто описывают работу методов, не нужны. Стоит удалять их перед коммитом.

    // Для внедрения свойств можно использовать аннотацию @Value.

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

    // В java методы принято называть глаголами или глагольными фразами.
        // В классах конфигурации исключение составляют методы, создающие бины.
        // Поэтому можно назвать этот метод getRequiredEnvironmentValue.
    private String requiredEnv(String name) { // Можно назвать аргумент String variableName
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required environment variable is not set: " + name
            );
        }

        return value;
    }
}