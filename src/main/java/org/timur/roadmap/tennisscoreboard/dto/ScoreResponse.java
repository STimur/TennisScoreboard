package org.timur.roadmap.tennisscoreboard.dto;

public record ScoreResponse(
        String firstPlayerName,
        String secondPlayerName,

        String firstPlayerPoints,
        String secondPlayerPoints,

        Integer firstPlayerGames,
        Integer secondPlayerGames,

        Integer firstPlayerSets,
        Integer secondPlayerSets,

        Integer firstPlayerTieBreakPoints,
        Integer secondPlayerTieBreakPoints,

        String winnerName
) {
}