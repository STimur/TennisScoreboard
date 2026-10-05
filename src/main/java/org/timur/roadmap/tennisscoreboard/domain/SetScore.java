package org.timur.roadmap.tennisscoreboard.domain;

public class SetScore {

    // Все магические числа лучше вынести в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    // TODO: Конструкторы с аргументами позволяют создать объект в невалидном состоянии.
        // Например, задать несуществующий в реальности отрицательный счёт.
        // В проекте не существует ситуации, где нужно было бы создавать сет с предустановленным счётом.
        // Удобство использования в тестах не оправдывает существование таких конструкторов —
        // тесты должны ПРИВОДИТЬ объект в нужное состояние, а НЕ СОЗДАВАТЬ его в нужном состоянии.
        // Эти конструкторы стоит удалить.

    // Логика в addFirstPlayerPoint() и в addSecondPlayerPoint() полностью дублируется по смыслу для обеих сторон.
        // Это нарушает принцип Don't Repeat Yourself (DRY) — "не повторяйся" и раздувает код и усложняет его поддержку.

    // Класс хранит в поле `winner` данные, которые являются производными от основного состояния (счёта).
        // Это нарушает Принцип Единого источника истины. Источником истины является счёт, поле `winner` — это лишь следствие.
        // Хранение производных данных создаёт риск рассинхронизации: можно изменить счёт,
        // но забыть обновить это поле, и объект окажется в неконсистентном состоянии.
        // Лучше удалить поле `winner` и заменить его методом, который вычисляет результат на лету из текущего счёта.
        // (см. файл "ssot-principle.md" в этом же пакете)

    // Вместо перезаписи одного поля и для гейма и для тай-брейка,
        // лучше хранить коллекцию GameScore и помещать в неё объекты по мере создания.
        // Это будет больше соответствовать реальному теннису, а также позволит вычислять победителя на лету.
    private GameScore currentGameScore;
    private int firstPlayerGames;
    private int secondPlayerGames;
    private PlayerSide winner;

    public SetScore() {
        currentGameScore = new OrdinaryGameScore();
        firstPlayerGames = 0;
        secondPlayerGames = 0;
        winner = null;
    }

    public SetScore(GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        currentGameScore = new OrdinaryGameScore(firstPlayerPoints, secondPlayerPoints);
    }

    public SetScore(int firstPlayerGames, int secondPlayerGames, GamePoint firstPlayerPoints, GamePoint secondPlayerPoints) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        currentGameScore = new OrdinaryGameScore(firstPlayerPoints, secondPlayerPoints);
    }

    public SetScore(int firstPlayerGames, int secondPlayerGames, int firstPlayerTieBreakPoints, int secondPlayerTieBreakPoints) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        currentGameScore = new TieBreakScore(firstPlayerTieBreakPoints, secondPlayerTieBreakPoints);
    }

    public void addFirstPlayerPoint() {

        // TODO: Нет проверки на то, что сет не завершён.
            // Попытка начислить очко в уже завершённом сете — это не нормальная ситуация и
            // должна приводить к исключению.

        currentGameScore.addFirstPlayerPoint();

        // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
        if (currentGameScore.isFinished()) {
            firstPlayerGames++;

            // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
            if (!isFinished()) {
                if (isTieBreakInProgress()) {
                    currentGameScore = new TieBreakScore();
                } else {
                    currentGameScore = new OrdinaryGameScore();
                }
                return;
            }
            winner = PlayerSide.FIRST;
        }
    }

    public void addSecondPlayerPoint() {

        // TODO: Нет проверки на то, что сет не завершён.
            // Попытка начислить очко в уже завершённом сете — это не нормальная ситуация и
            // должна приводить к исключению.

        currentGameScore.addSecondPlayerPoint();

        // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
        if (currentGameScore.isFinished()) {
            secondPlayerGames++;

            // Условие этого if можно инвертировать — это уменьшит вложенность кода и улучшит его читаемость.
            if (!isFinished()) {
                if (isTieBreakInProgress()) {
                    currentGameScore = new TieBreakScore();
                } else {
                    currentGameScore = new OrdinaryGameScore();
                }
                return;
            }
            winner = PlayerSide.SECOND;
        }
    }

    public boolean isFinished() {
        // Тело блоков if-else всегда следует оборачивать в {}
        if (firstPlayerGames == 6 && secondPlayerGames < 5)
            return true;
        if (secondPlayerGames == 6 && firstPlayerGames < 5)
            return true;
        return firstPlayerGames == 7 || secondPlayerGames == 7;
    }

    public int getFirstPlayerGames() {
        return firstPlayerGames;
    }

    public int getSecondPlayerGames() {
        return secondPlayerGames;
    }

    // Победитель есть не всегда, поэтому лучше возвращать Optional<PlayerSide>, чтобы метод никогда не возвращал null.
    public PlayerSide getWinner() {
        return winner;
    }

    public String getFirstPlayerPoints() {
        return currentGameScore.getFirstPlayerPoints();
    }

    public String getSecondPlayerPoints() {
        return currentGameScore.getSecondPlayerPoints();
    }

    // Проверить идёт ли ещё тай-брейк в текущей реализации можно так:
        // return currentGameScore instanceof TieBreakScore tieBreakScore && !tieBreakScore.isFinished();
        // Это позволит тай-брейку самому отвечать, завершён он или ещё идёт.
    // А метод, проверяющий должен ли начаться тай-брейк, лучше назвать shouldStartTieBreak()
    public boolean isTieBreakInProgress() {
        return firstPlayerGames == 6 && secondPlayerGames == 6;
    }
}
