package org.timur.roadmap.tennisscoreboard.domain;

public interface GameScore {

    // Вместо addFirstPlayerPoint() и addSecondPlayerPoint() лучше иметь метод `void addPointsFor(PlayerSide side)`.
        // Это избавит от дублирования кода.

    // Вместо getFirstPlayerPoints() и getSecondPlayerPoints() лучше иметь метод `String getPointsFor(PlayerSide side)`.
        // Это избавит от дублирования кода в маппере.

    // Методы getFirstPlayerPoints() и getSecondPlayerPoints() возвращают String, а не типы объектов счёта (int или GamePoint),
        // поэтому в текущей реализации точнее было бы дать им суффикс *AsString.

    // Методы getFirstPlayerPoints() и getSecondPlayerPoints() возвращают String,
        // что наделяет классы моделей не свойственной им ответственностью за то, как должен отображаться счёт.

    void addFirstPlayerPoint();
    void addSecondPlayerPoint();
    boolean isFinished();
    String getFirstPlayerPoints();
    String getSecondPlayerPoints();

    // Победитель есть не всегда, поэтому лучше возвращать Optional<PlayerSide>, чтобы метод никогда не возвращал null.
    PlayerSide getWinner();
}
