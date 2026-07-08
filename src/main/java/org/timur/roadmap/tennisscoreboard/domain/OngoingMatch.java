package org.timur.roadmap.tennisscoreboard.domain;

import org.timur.roadmap.tennisscoreboard.entity.Player;

import java.util.UUID;

public class OngoingMatch {
    private final UUID id;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Score score;

    public OngoingMatch(UUID id, Player firstPlayer, Player secondPlayer) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public UUID getId() {
        return id;
    }
}