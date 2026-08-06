package org.timur.roadmap.tennisscoreboard.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchRequest;
import org.timur.roadmap.tennisscoreboard.dto.CreateMatchResponse;
import org.timur.roadmap.tennisscoreboard.service.MatchService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class,
        HibernateConfig.class,
        FlywayConfig.class,
        DataSourceConfig.class
})
@WebAppConfiguration
class MatchControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MatchService matchService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void whenMatchIsOverAddingPointShouldReturn404WithErrorMessage() throws Exception {
        CreateMatchResponse resp = matchService.createMatch(new CreateMatchRequest("Player 1", "Player 2"));

        UUID matchId = resp.id();


        for (int i = 0; i < 48; i++) { // 48 is 6:0 6:0 win
            mockMvc.perform(
                    post("/matches/{id}/point", matchId)
                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )
                            .content("""
                                    {
                                      "name": "Player 1"
                                    }
                                    """)
            );
        }

        // match should have been removed from ongoing matches
        // so api call should return 404 message i guess
        mockMvc.perform(
                post("/matches/{id}/point", matchId)
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content("""
                                    {
                                      "name": "Player 1"
                                    }
                                    """)
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.message")
                                .value("Матч с таким uuid не найден")
                );
    }

    @Test
    void shouldReturn404WithErrorMessageWhenGettingScoreOfNotExistingMatch() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(
                get("/matches/{uuid}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.message")
                                .value("Матч с таким uuid не найден")
                );
    }
}
