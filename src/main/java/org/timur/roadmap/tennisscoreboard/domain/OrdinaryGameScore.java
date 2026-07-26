package org.timur.roadmap.tennisscoreboard.domain;

public class OrdinaryGameScore implements GameScore {

    private GamePoint firstPlayerPoints;
    private GamePoint secondPlayerPoints;
    private boolean isFinished;
    private PlayerSide winner;

    public OrdinaryGameScore() {
        firstPlayerPoints = GamePoint.LOVE;
        secondPlayerPoints = GamePoint.LOVE;
        isFinished = false;
        winner = null;
    }

    public OrdinaryGameScore(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        isFinished = false;
        winner = null;
    }

    @Override
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

    public String getFirstPlayerPoints() {
        return firstPlayerPoints.toString();
    }

    @Override
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

    public String getSecondPlayerPoints() {
        return secondPlayerPoints.toString();
    }

    public PlayerSide getWinner() {
        return winner;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
