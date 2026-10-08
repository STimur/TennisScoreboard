package org.timur.roadmap.tennisscoreboard.domain;

public enum GamePoint {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    AD("AD");

    // Название displayName семантически связывает этот класс с логикой представления (View),
        // хотя он относится к доменному слою.
        // Лучше назвать поле stringValue.
    private final String displayName;

    GamePoint(String displayName) {
        this.displayName = displayName;
    }

    public GamePoint next() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;

            // И в логике гейма и порядке значений в этом enum после FORTY следует ADVANTAGE.
                // Поэтому стоит для FORTY возвращать ADVANTAGE и бросать исключение только на ADVANTAGE.
            default -> throw new IllegalStateException(); // Лучше использовать информативные сообщения в исключениях.
        };
    }

    // Метод toString лучше использовать только для отладки.
        // А для возврата значения displayName создать геттер для этого поля.
    @Override
    public String toString() {
        return displayName;
    }
}