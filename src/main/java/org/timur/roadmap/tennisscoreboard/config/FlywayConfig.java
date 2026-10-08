package org.timur.roadmap.tennisscoreboard.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class FlywayConfig {

    // Использование `System.out` для логирования — плохая практика в серверных приложениях.
        // Логи должны выводиться через специализированный фреймворк.

    // Создание SessionFactory в HibernateConfig помечено @DependsOn("flyway"),
        // поэтому стоит явно задать это имя для бина Flyway.
        // Иначе переименование этого метода сломает запуск приложения.
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

        // Выполнение метода migrate() относится к жизненному циклу бина, а не к его конструированию.
            // То есть логика не такая: нужно выполнить migrate(), чтобы получить правильно созданный объект.
            // А такая: когда Spring создаст бин, нужно чтобы он перешёл в состояние started.
            // Поэтому запуск этого метода стоит указать в @Bean(initMethod = "migrate").
        flyway.migrate();

        return flyway;
    }
}