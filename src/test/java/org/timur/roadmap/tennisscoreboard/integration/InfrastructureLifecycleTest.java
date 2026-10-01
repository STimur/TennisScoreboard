package org.timur.roadmap.tennisscoreboard.integration;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.timur.roadmap.tennisscoreboard.config.DataSourceConfig;
import org.timur.roadmap.tennisscoreboard.config.FlywayConfig;
import org.timur.roadmap.tennisscoreboard.config.HibernateConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class InfrastructureLifecycleTest {

    @Test
    void closesInfrastructureBeansOnContextShutdown() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        DataSourceConfig.class,
                        FlywayConfig.class,
                        HibernateConfig.class
                );

        try {
            SessionFactory sessionFactory =
                    context.getBean(SessionFactory.class);

            HikariDataSource dataSource =
                    context.getBean(HikariDataSource.class);

            assertTrue(sessionFactory.isOpen());
            assertFalse(dataSource.isClosed());

            context.close();

            assertFalse(sessionFactory.isOpen());
            assertTrue(dataSource.isClosed());
        } finally {
            if (context.isActive()) {
                context.close();
            }
        }
    }
}
