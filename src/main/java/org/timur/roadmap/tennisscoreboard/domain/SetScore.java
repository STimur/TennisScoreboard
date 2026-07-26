package org.timur.roadmap.tennisscoreboard.domain;

public class SetScore {

    private GameScore currentGameScore;
    private int firstPlayerGames;
    private int secondPlayerGames;
    private PlayerSide winner;

    public SetScore() {
        currentGameScore = new OrdinaryGameScore();
        firstPlayerGames = 0;
        secondPlayerGames = 0;
        winner = null;
    }

    public SetScore(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        currentGameScore = new OrdinaryGameScore(firstPlayerPoints, secondPlayerPoints);
    }

    public SetScore(int firstPlayerGames, int secondPlayerGames, GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        currentGameScore = new OrdinaryGameScore(firstPlayerPoints, secondPlayerPoints);
    }

    public SetScore(int firstPlayerGames, int secondPlayerGames, int firstPlayerTieBreakPoints, int secondPlayerTieBreakPoints) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        currentGameScore = new TieBreakScore(firstPlayerTieBreakPoints, secondPlayerTieBreakPoints);
    }

    public void addFirstPlayerPoint() {
        currentGameScore.addFirstPlayerPoint();
        if (currentGameScore.isFinished()) {
            firstPlayerGames++;
            if (!isFinished()) {
                if (isTieBreakInProgress()) {
                    currentGameScore = new TieBreakScore();
                } else {
                    currentGameScore = new OrdinaryGameScore();
                }
                return;
            }
            winner = PlayerSide.FIRST;
        }
    }

    public void addSecondPlayerPoint() {
        currentGameScore.addSecondPlayerPoint();
        if (currentGameScore.isFinished()) {
            secondPlayerGames++;
            if (!isFinished()) {
                if (isTieBreakInProgress()) {
                    currentGameScore = new TieBreakScore();
                } else {
                    currentGameScore = new OrdinaryGameScore();
                }
                return;
            }
            winner = PlayerSide.SECOND;
        }
    }

    public boolean isFinished() {
        if (firstPlayerGames == 6 && secondPlayerGames < 5)
            return true;
        if (secondPlayerGames == 6 && firstPlayerGames < 5)
            return true;
        return firstPlayerGames == 7 || secondPlayerGames == 7;
    }

    public int getFirstPlayerGames() {
        return firstPlayerGames;
    }

    public int getSecondPlayerGames() {
        return secondPlayerGames;
    }

    public PlayerSide getWinner() {
        return winner;
    }

    public String getFirstPlayerPoints() {
        return currentGameScore.getFirstPlayerPoints();
    }

    public String getSecondPlayerPoints() {
        return currentGameScore.getSecondPlayerPoints();
    }

    public boolean isTieBreakInProgress() {
        return firstPlayerGames == 6 && secondPlayerGames == 6;
    }
}
