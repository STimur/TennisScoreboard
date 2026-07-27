package org.timur.roadmap.tennisscoreboard.domain;

import java.util.UUID;

public class OngoingMatch {
    private final UUID id;
    private final MatchScore score;

    public OngoingMatch(UUID id, String firstPlayerName, String secondPlayerName) {
        this.id = id;
        this.score = new MatchScore(firstPlayerName, secondPlayerName);
    }

    public UUID getId() {
        return id;
    }

    public MatchScore getScore() {
        return score;
    }

    public boolean isFinished() {
        return false;
    }

    public void addPoint(String playerName) {
        score.addPoint(playerName);
    }
}