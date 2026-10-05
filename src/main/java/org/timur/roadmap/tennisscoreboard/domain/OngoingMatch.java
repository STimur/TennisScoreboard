package org.timur.roadmap.tennisscoreboard.domain;

import java.util.UUID;

public class OngoingMatch {

    // Объект MatchScore отдаётся наружу для получения счёта в нём.
        // Есть несколько подходов — или позволить мапперу
        // получать объекты моделей и напрямую запрашивать у них счёт,
        // или в верхнем классе (OngoingMatch) создать все методы, возвращающие счёт
        // (или его строковое представление, как сейчас) на каждом этапе.
        // Оба решения компромиссные.
        // Третий вариант — создать классы, хранящие пары значений счёта (или использовать существующий Score)
        // и возвращать из верхнего уровня (OngoingMatch) их.
        // Этот вариант тоже имеет свои минусы, но зато не отдаёт классы моделей за пределы их слоя,
        // а также не заставляет модели знать о способе отображения счёта.

    private final UUID id;
    private final MatchScore score;

    public OngoingMatch(UUID id, String firstPlayerName, String secondPlayerName) {
        this.id = id;
        this.score = new MatchScore(firstPlayerName, secondPlayerName);
    }

    public UUID getId() {
        return id;
    }

    public MatchScore getScore() {
        return score;
    }

    public boolean isFinished() {
        return score.isFinished();
    }

    // В этом методе стоит преобразовывать String playerName —> PlayerSide и передавать в MatchScore уже сторону.
    public void addPoint(String playerName) {

        // TODO: Нет проверки на то, что матч не завершён.
            // Попытка начислить очко в уже завершённом матче — это не нормальная ситуация и
            // должна приводить к исключению.

        score.addPoint(playerName);
    }

    public String getFirstPlayerName() {
        return score.getFirstPlayerName();
    }

    public String getSecondPlayerName() {
        return score.getSecondPlayerName();
    }

    // Победитель есть не всегда, поэтому лучше возвращать Optional<String>, чтобы метод никогда не возвращал null.
    public String getWinnerName() {
        return score.getWinnerName();
    }
}