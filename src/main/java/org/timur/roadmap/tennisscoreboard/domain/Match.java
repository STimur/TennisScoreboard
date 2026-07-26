package org.timur.roadmap.tennisscoreboard.domain;

public class Match {

    private final String firstPlayerName;
    private final String secondPlayerName;
    private int firstPlayerSets;
    private int secondPlayerSets;
    private Set currentSet;
    private boolean isFinished;
    private String winnerName;

    public Match(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = 0;
        this.secondPlayerSets = 0;
        this.currentSet = new Set();
        isFinished = false;
        winnerName = null;
    }

    public Match(String firstPlayerName, String secondPlayerName, int firstPlayerSets,
                 int secondPlayerSets, int firstPlayerGames, int secondPlayerGames,
                 GamePoint firstPlayersPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.currentSet = new Set(firstPlayerGames, secondPlayerGames, firstPlayersPoints, secondPlayerPoints);
        isFinished = false;
        winnerName = null;
    }

    public Match(String firstPlayerName, String secondPlayerName, int firstPlayerSets,
                 int secondPlayerSets, int firstPlayerGames, int secondPlayerGames,
                 int firstPlayerTieBreakPoints, int secondPlayerTieBreakPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.currentSet = new Set(firstPlayerGames, secondPlayerGames,
                firstPlayerTieBreakPoints, secondPlayerTieBreakPoints);
        isFinished = false;
        winnerName = null;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public String getSecondPlayer() {
        return secondPlayerName;
    }

    public void addPoint(String playerName) {
        if (playerName.equals(firstPlayerName)) {
            currentSet.addFirstPlayerPoint();
            if (currentSet.isFinished()) {
                firstPlayerSets++;
                if (firstPlayerSets == 2) {
                    winnerName = firstPlayerName;
                    isFinished = true;
                    return;
                }
                currentSet = new Set();
            }
        } else {
            currentSet.addSecondPlayerPoint();
            if (currentSet.isFinished()) {
                secondPlayerSets++;
                if (secondPlayerSets == 2) {
                    winnerName = secondPlayerName;
                    isFinished = true;
                    return;
                }
                currentSet = new Set();
            }
        }
    }

    public String getFirstPlayerPoints() {
        if (isFinished || isTieBreakInProgress())
            return null;

        return currentSet.getFirstPlayerPoints();
    }

    private boolean isTieBreakInProgress() {
        return currentSet.isTieBreakInProgress();
    }

    public Integer getFirstPlayerGames() {
        if (isFinished)
            return null;

        return currentSet.getFirstPlayerGames();
    }

    public int getFirstPlayerSets() {
        return firstPlayerSets;
    }

    public Integer getFirstPlayerTieBreakPoints() {
        if (!isTieBreakInProgress())
            return null;

        return Integer.valueOf(currentSet.getFirstPlayerPoints());
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public String getSecondPlayerPoints() {
        if (isFinished || isTieBreakInProgress())
            return null;

        return currentSet.getSecondPlayerPoints();
    }

    public Integer getSecondPlayerGames() {
        if (isFinished)
            return null;

        return currentSet.getSecondPlayerGames();
    }

    public int getSecondPlayerSets() {
        return secondPlayerSets;
    }

    public Integer getSecondPlayerTieBreakPoints() {
        if (!isTieBreakInProgress())
            return null;

        return Integer.valueOf(currentSet.getSecondPlayerPoints());
    }

    public String getWinnerName() {
        return winnerName;
    }
}
