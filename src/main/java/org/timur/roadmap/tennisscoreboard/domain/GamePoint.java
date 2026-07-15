package org.timur.roadmap.tennisscoreboard.domain;

public enum GamePoint {
    LOVE,
    FIFTEEN,
    THIRTY,
    FORTY,
    AD;

    public GamePoint next() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            default -> throw new IllegalStateException();
        };
    }
}