package org.timur.roadmap.tennisscoreboard;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) throws SQLException {
        System.out.println("DB URL = " + dataSource.getConnection().getMetaData().getURL());
        System.out.println("DB Product = " + dataSource.getConnection().getMetaData().getDatabaseProductName());
        System.out.println("DB Version = " + dataSource.getConnection().getMetaData().getDatabaseProductVersion());

        System.out.println("CLASS LOADED FROM: " +
                Flyway.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation());

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        System.out.println("Flyway version = " +
                Flyway.class.getPackage().getImplementationVersion());

        flyway.migrate();

        return flyway;
    }
}