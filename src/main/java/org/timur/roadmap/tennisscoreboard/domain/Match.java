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
                }
            }

        } else {
            currentSet.addSecondPlayerPoint();

            if (currentSet.isFinished()) {
                secondPlayerSets++;

                if (secondPlayerSets == 2) {
                    winnerName = secondPlayerName;
                    isFinished = true;
                }
            }
        }
    }

    public String getFirstPlayerPoints() {
        return currentSet.getFirstPlayerPoints();
    }

    public int getFirstPlayerGames() {
        return 0;
    }

    public int getFirstPlayerSets() {
        return 0;
    }

    public Integer getFirstPlayerTieBreakPoints() {
        return null;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public String getSecondPlayerPoints() {
        return currentSet.getSecondPlayerPoints();
    }

    public int getSecondPlayerGames() {
        return 0;
    }

    public int getSecondPlayerSets() {
        return 0;
    }

    public Integer getSecondPlayerTieBreakPoints() {
        return null;
    }

    public String getWinnerName() {
        return null;
    }
}
