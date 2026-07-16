package org.timur.roadmap.tennisscoreboard.domain;

public class Game {

    private GamePoint firstPlayerPoints;
    private GamePoint secondPlayerPoints;
    private boolean isFinished;
    private PlayerSide winner;

    public Game() {
        firstPlayerPoints = GamePoint.LOVE;
        secondPlayerPoints = GamePoint.LOVE;
        isFinished = false;
        winner = null;
    }

    public Game(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        isFinished = false;
        winner = null;
    }

    public void addFirstPlayerPoint() {
        if (firstPlayerPoints.equals(GamePoint.AD)) {
            isFinished = true;
            winner = PlayerSide.FIRST;
            return;
        }
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
            winner = PlayerSide.FIRST;
            return;
        }
        firstPlayerPoints = firstPlayerPoints.next();
    }

    public GamePoint getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public void addSecondPlayerPoint() {
        if (secondPlayerPoints.equals(GamePoint.AD)) {
            isFinished = true;
            winner = PlayerSide.SECOND;
            return;
        }
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
            winner = PlayerSide.SECOND;
            return;
        }
        secondPlayerPoints = secondPlayerPoints.next();
    }

    public GamePoint getSecondPlayerPoints() {
        return secondPlayerPoints;
    }

    public PlayerSide getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
