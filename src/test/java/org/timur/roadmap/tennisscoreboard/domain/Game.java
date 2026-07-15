package org.timur.roadmap.tennisscoreboard.domain;

public class Game {

    private GamePoint firstPlayerPoints;
    private GamePoint secondPlayerPoints;

    public Game() {
        firstPlayerPoints = GamePoint.LOVE;
        secondPlayerPoints = GamePoint.LOVE;
    }

    public void addFirstPlayerPoint() {
        firstPlayerPoints = firstPlayerPoints.next();
    }

    public GamePoint getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public void addSecondPlayerPoint() {
        secondPlayerPoints = secondPlayerPoints.next();
    }

    public GamePoint getSecondPlayerPoints() {
        return secondPlayerPoints;
    }
}
