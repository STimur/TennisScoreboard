package org.timur.roadmap.tennisscoreboard.domain;

public record Score(
        int firstPlayerPoints,
        int secondPlayerPoints,
        int firstPlayerGames,
        int secondPlayerGames,
        int firstPlayerSets,
        int secondPlayerSets,
        int firstPlayerTieBreakPoints,
        int secondPlayerTieBreakPoints
) {

    // Класс нигде не используется в проекте. В текущей реализации его можно удалить.

}
