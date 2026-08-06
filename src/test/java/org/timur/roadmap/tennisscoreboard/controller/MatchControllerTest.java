package org.timur.roadmap.tennisscoreboard.controller;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.timur.roadmap.tennisscoreboard.service.MatchService;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MatchControllerTest {

    private MockMvc mockMvc;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = Mockito.mock(MatchService.class);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new MatchController(matchService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldReturn400WhenFirstPlayerIsBlank() throws Exception {
        mockMvc.perform(
                        post("/matches")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "firstPlayerName": "",
                                  "secondPlayerName": "Nadal"
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Имя первого игрока не должно быть пустым")
                );

        verifyNoInteractions(matchService);
    }
}
