package org.timur.roadmap.tennisscoreboard.domain;

public class Set {

    private Game currentGame;
    private int firstPlayerGames;
    private int secondPlayerGames;

    public Set() {
        currentGame = new OrdinaryGame();
        firstPlayerGames = 0;
        secondPlayerGames = 0;
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
                currentGame = new OrdinaryGame();
            }
        }
    }

    public void addSecondPlayerPoint() {
        currentGame.addSecondPlayerPoint();
        if (currentGame.isFinished()) {
            secondPlayerGames++;
            if (!isFinished()) {
                currentGame = new OrdinaryGame();
            }
        }
    }

    public Game getCurrentGame() {
        if (isFinished()) {
            throw new SetFinishedException();
        }
        return currentGame;
    }

    public boolean isFinished() {
        if (firstPlayerGames == 6 && secondPlayerGames < 5)
            return true;
        if (secondPlayerGames == 6 && firstPlayerGames < 5)
            return true;
        if (firstPlayerGames == 7 || secondPlayerGames == 7)
            return true;

        return false;
    }

    public int getFirstPlayerGames() {
        return firstPlayerGames;
    }

    public int getSecondPlayerGames() {
        return secondPlayerGames;
    }

    public static class SetFinishedException extends RuntimeException {
    }
}
