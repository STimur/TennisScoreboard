package org.timur.roadmap.tennisscoreboard.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.timur.roadmap.tennisscoreboard.config.DataSourceConfig;
import org.timur.roadmap.tennisscoreboard.config.FlywayConfig;
import org.timur.roadmap.tennisscoreboard.config.HibernateConfig;
import org.timur.roadmap.tennisscoreboard.config.WebConfig;
import org.timur.roadmap.tennisscoreboard.exception.DataAccessException;
import org.timur.roadmap.tennisscoreboard.service.MatchService;
import org.timur.roadmap.tennisscoreboard.support.SwitchableDataSource;
import org.timur.roadmap.tennisscoreboardtest.config.TestDatabaseConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class,
        HibernateConfig.class,
        FlywayConfig.class,
        DataSourceConfig.class,
        TestDatabaseConfig.class
})
@WebAppConfiguration
class DatabaseFailureTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private SwitchableDataSource dataSource;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    void tearDown() {
        dataSource.recover();
    }

    @Test
    void shouldReturn500WhenDatabaseAccessFails() throws Exception {
        dataSource.fail();

        mockMvc.perform(
                        get("/matches")
                                .param("page", "1")
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message")
                        .value(DataAccessException.MESSAGE));
    }
}
