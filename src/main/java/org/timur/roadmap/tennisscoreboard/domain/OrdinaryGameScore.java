package org.timur.roadmap.tennisscoreboard.domain;

public class OrdinaryGameScore implements GameScore {

    // Класс хранит в поле `boolean isFinished` данные, которые являются производными от основного состояния (счёта).
        // Завершение гейма уже обозначается установкой победителя `PlayerSide winner`.
        // Поэтому поле `boolean isFinished` избыточно и нарушает Принцип Единого источника истины.
        // (см. файл "ssot-principle.md" в этом же пакете)
        // Хранение идентичные по смыслу данных в разных полях создаёт риск рассинхронизации: можно установить победителя,
        // но забыть обновить поле флага, и объект окажется в неконсистентном состоянии.
        // Лучше удалить поле `isFinished` и определять завершён ли гейм через winner == null.

    // Методы, которые реализуют методы интерфейса, должны быть помечены аннотацией @Override.

    // Логика в addFirstPlayerPoint() и в addSecondPlayerPoint() полностью дублируется по смыслу для обеих сторон.
        // Это нарушает принцип Don't Repeat Yourself (DRY) — "не повторяйся" и раздувает код и усложняет его поддержку.

    // Значения enum можно сравнивать через == вместо equals.

    private GamePoint firstPlayerPoints;
    private GamePoint secondPlayerPoints;
    private boolean isFinished;
    private PlayerSide winner;

    public OrdinaryGameScore() {
        firstPlayerPoints = GamePoint.LOVE;
        secondPlayerPoints = GamePoint.LOVE;
        isFinished = false;
        winner = null;
    }

    // TODO: Этот конструктор позволяет создать объект в невалидном состоянии:
        // например, new OrdinaryGameScore(GamePoint.AD, GamePoint.AD).
        // В проекте не существует ситуации, где нужно было бы создавать гейм с предустановленным счётом.
        // Удобство использования в тестах не оправдывает существование такого конструктора —
        // тесты должны ПРИВОДИТЬ объект в нужное состояние, а НЕ СОЗДАВАТЬ его в нужном состоянии.
        // Этот конструктор стоит удалить.
    public OrdinaryGameScore(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        isFinished = false;
        winner = null;
    }

    @Override
    public void addFirstPlayerPoint() {

        // TODO: Нет проверки на то, что гейм не завершён.
            // Попытка начислить очко в уже завершённом гейме — это не нормальная ситуация и
            // должна приводить к исключению.

        if (firstPlayerPoints.equals(GamePoint.AD)) {
            isFinished = true;
            winner = PlayerSide.FIRST;
            return;
        }

        // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
        if (firstPlayerPoints.equals(GamePoint.FORTY)) {
            if (secondPlayerPoints.equals(GamePoint.FORTY)) {
                firstPlayerPoints = GamePoint.AD;
                return;
            }
            if (secondPlayerPoints.equals(GamePoint.AD)) {
                secondPlayerPoints = GamePoint.FORTY;
                return;
            }
            isFinished = true;
            winner = PlayerSide.FIRST;
            return;
        }
        firstPlayerPoints = firstPlayerPoints.next();
    }

    public String getFirstPlayerPoints() {
        return firstPlayerPoints.toString();
    }

    @Override
    public void addSecondPlayerPoint() {

        // TODO: Нет проверки на то, что гейм не завершён.
            // Попытка начислить очко в уже завершённом гейме — это не нормальная ситуация и
            // должна приводить к исключению.

        if (secondPlayerPoints.equals(GamePoint.AD)) {
            isFinished = true;
            winner = PlayerSide.SECOND;
            return;
        }

        // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
        if (secondPlayerPoints.equals(GamePoint.FORTY)) {
            if (firstPlayerPoints.equals(GamePoint.FORTY)) {
                secondPlayerPoints = GamePoint.AD;
                return;
            }
            if (firstPlayerPoints.equals(GamePoint.AD)) {
                firstPlayerPoints = GamePoint.FORTY;
                return;
            }
            isFinished = true;
            winner = PlayerSide.SECOND;
            return;
        }
        secondPlayerPoints = secondPlayerPoints.next();
    }

    public String getSecondPlayerPoints() {
        return secondPlayerPoints.toString();
    }

    // Победитель есть не всегда, поэтому лучше возвращать Optional<PlayerSide>, чтобы метод никогда не возвращал null.
    public PlayerSide getWinner() {
        return winner;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
