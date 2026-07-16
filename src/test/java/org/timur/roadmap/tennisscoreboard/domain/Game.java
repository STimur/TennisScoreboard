package org.timur.roadmap.tennisscoreboard.domain;

public class Game {

    private GamePoint firstPlayerPoints;
    private GamePoint secondPlayerPoints;
    private boolean isFinished;
    private int winner;

    public Game() {
        firstPlayerPoints = GamePoint.LOVE;
        secondPlayerPoints = GamePoint.LOVE;
        isFinished = false;
        winner = 0;
    }

    public Game(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        isFinished = false;
        winner = 0;
    }

    public void addFirstPlayerPoint() {
        if (firstPlayerPoints.equals(GamePoint.FORTY)) {
            if (secondPlayerPoints.equals(GamePoint.FORTY)) {
                firstPlayerPoints = GamePoint.AD;
                return;
            }
            if (secondPlayerPoints.equals(GamePoint.AD)) {
                secondPlayerPoints = GamePoint.FORTY;
                return;
            }
            isFinished = true;
            winner = 1;
            return;
        }
        firstPlayerPoints = firstPlayerPoints.next();
    }

    public GamePoint getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public void addSecondPlayerPoint() {
        if (secondPlayerPoints.equals(GamePoint.FORTY)) {
            if (firstPlayerPoints.equals(GamePoint.FORTY)) {
                secondPlayerPoints = GamePoint.AD;
                return;
            }
            if (firstPlayerPoints.equals(GamePoint.AD)) {
                firstPlayerPoints = GamePoint.FORTY;
                return;
            }
            isFinished = true;
            winner = 2;
            return;
        }
        secondPlayerPoints = secondPlayerPoints.next();
    }

    public GamePoint getSecondPlayerPoints() {
        return secondPlayerPoints;
    }

    public int getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
