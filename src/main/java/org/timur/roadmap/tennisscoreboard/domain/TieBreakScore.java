package org.timur.roadmap.tennisscoreboard.domain;

public class TieBreakScore implements GameScore {

    private Integer firstPlayerPoints;
    private Integer secondPlayerPoints;
    private boolean isFinished;
    private PlayerSide winner;

    public TieBreakScore(int firstPlayerPoints, int secondPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        isFinished = false;
        winner = null;
    }

    public TieBreakScore() {
        firstPlayerPoints = 0;
        secondPlayerPoints = 0;
        isFinished = false;
        winner = null;
    }

    @Override
    public void addFirstPlayerPoint() {
        firstPlayerPoints++;
        if (firstPlayerPoints > 6 && (firstPlayerPoints - secondPlayerPoints) >= 2) {
            isFinished = true;
            winner = PlayerSide.FIRST;
        }
    }

    @Override
    public void addSecondPlayerPoint() {
        secondPlayerPoints++;
        if (secondPlayerPoints > 6 && (secondPlayerPoints - firstPlayerPoints) >= 2) {
            isFinished = true;
            winner = PlayerSide.SECOND;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String getFirstPlayerPoints() {
        return firstPlayerPoints.toString();
    }

    @Override
    public String getSecondPlayerPoints() {
        return secondPlayerPoints.toString();
    }

    @Override
    public PlayerSide getWinner() {
        return winner;
    }
}
