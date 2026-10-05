package org.timur.roadmap.tennisscoreboard.domain;

public class MatchScore {

    // Все магические числа лучше вынести в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    // TODO: Конструкторы с аргументами позволяют создать объект в невалидном состоянии.
        // Например, задать несуществующий в реальности отрицательный счёт.
        // В проекте не существует ситуации, где нужно было бы создавать матч с предустановленным счётом.
        // Удобство использования в тестах не оправдывает существование таких конструкторов —
        // тесты должны ПРИВОДИТЬ объект в нужное состояние, а НЕ СОЗДАВАТЬ его в нужном состоянии.
        // Эти конструкторы стоит удалить.

    // Логика в addPoint() полностью дублируется по смыслу для обеих сторон.
        // Это нарушает принцип Don't Repeat Yourself (DRY) — "не повторяйся" и раздувает код и усложняет его поддержку.

    // Класс хранит в полях `isFinished` и `winnerName` данные, которые являются производными от основного состояния (счёта).
        // Это нарушает Принцип Единого источника истины. Источником истины является счёт, поля `isFinished` и `winnerName` — это лишь следствие.
        // Хранение производных данных создаёт риск рассинхронизации: можно изменить счёт,
        // но забыть обновить поле, и объект окажется в неконсистентном состоянии.
        // Лучше удалить поля `isFinished` и `winnerName` и заменить их методами, которые вычисляют результат на лету из текущего счёта.
        // (см. файл "ssot-principle.md" в этом же пакете)

    // Вместо перезаписи одного поля и того же поля для каждого нового сета,
        // лучше хранить коллекцию SetScore и помещать в неё объекты по мере создания.
        // Это будет больше соответствовать реальному теннису, а также позволит вычислять победителя на лету.

    // TODO: Класс нарушает Принцип единой ответственности (SRP). Он:
            // - отвечает за обработку счёта в матче
            // - занимается маппингом счёта для отображения
            // - занимается маппингом String playerName <—> PlayerSide (неявно в addPoint)
        // Ответственность за преобразование PlayerSide <—> String playerName должна быть в OngoingMatch.
        // Логику преобразования счёта в String можно добавить в каждый класс модели (если она остаётся в моделях)
        // или вынести на самый верхний уровень (OngoingMatch) или в маппер.

    private final String firstPlayerName;
    private final String secondPlayerName;
    private int firstPlayerSets;
    private int secondPlayerSets;
    private SetScore currentSetScore;
    private boolean isFinished;
    private String winnerName;

    public MatchScore(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = 0;
        this.secondPlayerSets = 0;
        this.currentSetScore = new SetScore();
        isFinished = false;
        winnerName = null;
    }

    public MatchScore(String firstPlayerName, String secondPlayerName, int firstPlayerSets,
                      int secondPlayerSets, int firstPlayerGames, int secondPlayerGames,
                      GamePoint firstPlayersPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.currentSetScore = new SetScore(firstPlayerGames, secondPlayerGames, firstPlayersPoints, secondPlayerPoints);
        isFinished = false;
        winnerName = null;
    }

    public MatchScore(String firstPlayerName, String secondPlayerName, int firstPlayerSets,
                      int secondPlayerSets, int firstPlayerGames, int secondPlayerGames,
                      int firstPlayerTieBreakPoints, int secondPlayerTieBreakPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.currentSetScore = new SetScore(firstPlayerGames, secondPlayerGames,
                firstPlayerTieBreakPoints, secondPlayerTieBreakPoints);
        isFinished = false;
        winnerName = null;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    // TODO: Метод возвращает String, хотя его название говорит о том, что он вернёт Player
    // Метод нигде не используется, поэтому стоит его удалить.
    public String getSecondPlayer() {
        return secondPlayerName;
    }

    public boolean isFinished() {
        return isFinished;
    }

    // Лучше в метод начисления очка принимать PlayerSide, как в других классах *Score.
        // Ответственность за преобразование PlayerSide <—> String playerName должна быть в OngoingMatch.
    public void addPoint(String playerName) {

        // TODO: Нет проверки на то, что матч не завершён.
            // Попытка начислить очко в уже завершённом матче — это не нормальная ситуация и
            // должна приводить к исключению.

        if (playerName.equals(firstPlayerName)) {
            currentSetScore.addFirstPlayerPoint();

            // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
            if (currentSetScore.isFinished()) {
                firstPlayerSets++;
                if (firstPlayerSets == 2) {
                    winnerName = firstPlayerName;
                    isFinished = true;
                    return;
                }
                currentSetScore = new SetScore();
            }
        } else {
            currentSetScore.addSecondPlayerPoint();

            // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
            if (currentSetScore.isFinished()) {
                secondPlayerSets++;
                if (secondPlayerSets == 2) {
                    winnerName = secondPlayerName;
                    isFinished = true;
                    return;
                }
                currentSetScore = new SetScore();
            }
        }
    }

    public String getFirstPlayerPoints() {

        // Тело блоков if-else всегда следует оборачивать в {}
        // Здесь достаточно проверки currentSetScore instanceof OrdinaryGameScore
        if (isFinished || isTieBreakInProgress())
            return null;

        return currentSetScore.getFirstPlayerPoints();
    }

    public Integer getFirstPlayerGames() {

        // Тело блоков if-else всегда следует оборачивать в {}
        if (isFinished)

            // Даже если матч не завершён, счёт в нём не равен null
            return null;

        return currentSetScore.getFirstPlayerGames();
    }

    public int getFirstPlayerSets() {
        return firstPlayerSets;
    }

    public Integer getFirstPlayerTieBreakPoints() {

        // Тело блоков if-else всегда следует оборачивать в {}
        // Здесь достаточно проверки currentSetScore instanceof TieBreakScore
        if (!isTieBreakInProgress())
            return null;

        return Integer.valueOf(currentSetScore.getFirstPlayerPoints());
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public String getSecondPlayerPoints() {

        // Тело блоков if-else всегда следует оборачивать в {}
        // Здесь достаточно проверки currentSetScore instanceof OrdinaryGameScore
        if (isFinished || isTieBreakInProgress())
            return null;

        return currentSetScore.getSecondPlayerPoints();
    }

    public Integer getSecondPlayerGames() {

        // Тело блоков if-else всегда следует оборачивать в {}
        if (isFinished)

            // Даже если матч не завершён, счёт в нём не равен null
            return null;

        return currentSetScore.getSecondPlayerGames();
    }

    public int getSecondPlayerSets() {
        return secondPlayerSets;
    }

    public Integer getSecondPlayerTieBreakPoints() {

        // Тело блоков if-else всегда следует оборачивать в {}
        // Здесь достаточно проверки currentSetScore instanceof TieBreakScore
        if (!isTieBreakInProgress())
            return null;

        return Integer.valueOf(currentSetScore.getSecondPlayerPoints());
    }

    // Метод должен возвращать сторону победителя, а ответственность за преобразование стороны в имя должна быть в OngoingMatch.
    // Победитель есть не всегда, поэтому лучше возвращать Optional<PlayerSide>, чтобы метод никогда не возвращал null.
    public String getWinnerName() {
        return winnerName;
    }

    private boolean isTieBreakInProgress() {
        return currentSetScore.isTieBreakInProgress();
    }
}
