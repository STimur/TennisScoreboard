package org.timur.roadmap.tennisscoreboard.domain;

public enum GamePoint {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    AD("AD");

    private final String displayName;

    GamePoint(String displayName) {
        this.displayName = displayName;
    }

    public GamePoint next() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String toString() {
        return displayName;
    }
}