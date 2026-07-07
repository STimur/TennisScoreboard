package org.timur.roadmap.tennisscoreboard.dto;

public record MatchDto(
        Integer id,
        String player1,
        String player2,
        String winner
) {
}