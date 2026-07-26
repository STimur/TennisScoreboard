package org.timur.roadmap.tennisscoreboard.dto;

public record ScoreResponse(
        PlayerScoreDto firstPlayer,
        PlayerScoreDto secondPlayer,
        String winnerName
) {
}