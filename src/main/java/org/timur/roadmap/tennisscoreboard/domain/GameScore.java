package org.timur.roadmap.tennisscoreboard.domain;

public interface GameScore {
    void addFirstPlayerPoint();
    void addSecondPlayerPoint();
    boolean isFinished();
    String getFirstPlayerPoints();
    String getSecondPlayerPoints();
    PlayerSide getWinner();
}
