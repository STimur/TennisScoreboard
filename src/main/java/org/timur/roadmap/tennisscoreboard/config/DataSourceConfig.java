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
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/tennis_scoreboard");
        ds.setUsername("tennis_app");
        ds.setPassword("secret");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setMaximumPoolSize(10);
        return ds;
    }
}