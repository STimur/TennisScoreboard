package org.timur.roadmap.tennisscoreboardtest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.timur.roadmap.tennisscoreboard.support.SwitchableDataSource;

import javax.sql.DataSource;

@Configuration
public class TestDatabaseConfig {

    @Bean
    @Primary
    public SwitchableDataSource testDataSource(
            @Qualifier("dataSource") DataSource realDataSource
    ) {
        return new SwitchableDataSource(realDataSource);
    }
}