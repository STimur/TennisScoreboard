package org.timur.roadmap.tennisscoreboard.domain;

public interface Game {
    void addFirstPlayerPoint();
    void addSecondPlayerPoint();
    boolean isFinished();
    String getFirstPlayerPoints();
    String getSecondPlayerPoints();
    PlayerSide getWinner();
}
