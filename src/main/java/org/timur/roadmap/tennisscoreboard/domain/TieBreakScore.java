package org.timur.roadmap.tennisscoreboard.domain;

public class TieBreakScore implements GameScore {

    // Класс хранит в полях `isFinished` и `winner` данные, которые являются производными от основного состояния (счёта).
        // Это нарушает Принцип Единого источника истины. Источником истины является счёт, поля `isFinished` и `winner` — это лишь следствие.
        // Хранение производных данных создаёт риск рассинхронизации: можно изменить счёт,
        // но забыть обновить поле, и объект окажется в неконсистентном состоянии.
        // Лучше удалить поля `isFinished` и `winner` и заменить их методами, которые вычисляют результат на лету из текущего счёта.
        // (см. файл "ssot-principle.md" в этом же пакете)

    // Все магические числа лучше вынести в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    // Логика в addFirstPlayerPoint() и в addSecondPlayerPoint() полностью дублируется по смыслу для обеих сторон.
        // Это нарушает принцип Don't Repeat Yourself (DRY) — "не повторяйся" и раздувает код и усложняет его поддержку.

    private Integer firstPlayerPoints; // Это поле не может быть null, поэтому можно использовать примитивный тип int
    private Integer secondPlayerPoints; // Это поле не может быть null, поэтому можно использовать примитивный тип int
    private boolean isFinished;
    private PlayerSide winner;

    // TODO: Этот конструктор позволяет создать объект в невалидном состоянии:
        // например, new TieBreakScore(47, 18).
        // В проекте не существует ситуации, где нужно было бы создавать тай-брейк с предустановленным счётом.
        // Удобство использования в тестах не оправдывает существование такого конструктора —
        // тесты должны ПРИВОДИТЬ объект в нужное состояние, а НЕ СОЗДАВАТЬ его в нужном состоянии.
        // Этот конструктор стоит удалить.
    public TieBreakScore(int firstPlayerPoints, int secondPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        isFinished = false;
        winner = null;
    }

    public TieBreakScore() {
        firstPlayerPoints = 0;
        secondPlayerPoints = 0;
        isFinished = false;
        winner = null;
    }

    @Override
    public void addFirstPlayerPoint() {

        // TODO: Нет проверки на то, что тай-брейк не завершён.
            // Попытка начислить очко в уже завершённом тай-брейке — это не нормальная ситуация и
            // должна приводить к исключению.

        firstPlayerPoints++;
        if (firstPlayerPoints > 6 && (firstPlayerPoints - secondPlayerPoints) >= 2) {
            isFinished = true;
            winner = PlayerSide.FIRST;
        }
    }

    @Override
    public void addSecondPlayerPoint() {

        // TODO: Нет проверки на то, что тай-брейк не завершён.
            // Попытка начислить очко в уже завершённом тай-брейке — это не нормальная ситуация и
            // должна приводить к исключению.

        secondPlayerPoints++;
        if (secondPlayerPoints > 6 && (secondPlayerPoints - firstPlayerPoints) >= 2) {
            isFinished = true;
            winner = PlayerSide.SECOND;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String getFirstPlayerPoints() {
        return firstPlayerPoints.toString();
    }

    @Override
    public String getSecondPlayerPoints() {
        return secondPlayerPoints.toString();
    }

    // Победитель есть не всегда, поэтому лучше возвращать Optional<PlayerSide>, чтобы метод никогда не возвращал null.
    @Override
    public PlayerSide getWinner() {
        return winner;
    }
}
