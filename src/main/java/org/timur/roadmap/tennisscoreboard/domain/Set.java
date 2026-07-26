package org.timur.roadmap.tennisscoreboard.domain;

public class Set {

    private Game currentGame;
    private int firstPlayerGames;
    private int secondPlayerGames;
    private PlayerSide winner;

    public Set() {
        currentGame = new OrdinaryGame();
        firstPlayerGames = 0;
        secondPlayerGames = 0;
        winner = null;
    }

    public Set(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        currentGame = new OrdinaryGame(firstPlayerPoints, secondPlayerPoints);
    }

    public Set(int firstPlayerGames, int secondPlayerGames, GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        currentGame = new OrdinaryGame(firstPlayerPoints, secondPlayerPoints);
    }

    public Set(int firstPlayerGames, int secondPlayerGames, int firstPlayerTieBreakPoints, int secondPlayerTieBreakPoints) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        currentGame = new TieBreak(firstPlayerTieBreakPoints, secondPlayerTieBreakPoints);
    }

    public void addFirstPlayerPoint() {
        currentGame.addFirstPlayerPoint();
        if (currentGame.isFinished()) {
            firstPlayerGames++;
            if (!isFinished()) {
                if (isTieBreakInProgress()) {
                    currentGame = new TieBreak();
                } else {
                    currentGame = new OrdinaryGame();
                }
                return;
            }
            winner = PlayerSide.FIRST;
        }
    }

    public void addSecondPlayerPoint() {
        currentGame.addSecondPlayerPoint();
        if (currentGame.isFinished()) {
            secondPlayerGames++;
            if (!isFinished()) {
                if (isTieBreakInProgress()) {
                    currentGame = new TieBreak();
                } else {
                    currentGame = new OrdinaryGame();
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
        return currentGame.getFirstPlayerPoints();
    }

    public String getSecondPlayerPoints() {
        return currentGame.getSecondPlayerPoints();
    }

    public boolean isTieBreakInProgress() {
        return firstPlayerGames == 6 && secondPlayerGames == 6;
    }
}
