package org.timur.roadmap.tennisscoreboard.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.timur.roadmap.tennisscoreboard.entity.Match;
import org.timur.roadmap.tennisscoreboard.entity.Player;

import javax.sql.DataSource;

@Configuration
public class HibernateConfig {

    @Bean
    @DependsOn("flyway")
    public SessionFactory sessionFactory(DataSource dataSource) {

            StandardServiceRegistry registry =
                    new StandardServiceRegistryBuilder()
                            .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                            .applySetting("hibernate.hbm2ddl.auto", "validate")
                            .applySetting("hibernate.show_sql", "true")
                            // Передаем DataSource от Spring
                            .applySetting("hibernate.connection.datasource", dataSource)
                            // ВАЖНО: говорим Hibernate привязывать сессию к текущему Thread
                            .applySetting("hibernate.current_session_context_class", "thread")
                            .build();

            MetadataSources sources = new MetadataSources(registry)
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(Match.class);

            return sources.buildMetadata().buildSessionFactory();
    }
}