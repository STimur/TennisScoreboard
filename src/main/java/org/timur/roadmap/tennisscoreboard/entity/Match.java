package org.timur.roadmap.tennisscoreboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches") // "matches" является зарезервированным словом в некоторых СУБД.
    // Здесь проблем не будет, но лучше не выбирать такие названия. (см. файл "sql-keywords.md" в этом же пакете)
public class Match {

    // Поля игроков используют числительные в виде цифр (`player1`, `player2`),
        // тогда как в другом месте приложения (в сервисе и в конструкторе) внутренние переменные,
        // обозначающие тех же игроков, именуются словами (`firstPlayer`, `secondPlayer`).
        // Отсутствие единого, последовательного стиля именования может затруднять чтение кода.
        // Разработчику приходится тратить дополнительное время и умственные усилия, чтобы убедиться,
        // что `player1` и `firstPlayer` являются обозначениями одной и той же сущности.
        // Это создаёт ненужную когнитивную нагрузку.
        //
        // Также непоследовательное именование повышает риск случайных ошибок,
        // особенно при копировании/вставке кода или при работе с большим количеством схожих переменных,
        // когда легко перепутать один стиль с другим.
        //
        // Стоит привести именование к единому стилю, используя либо только цифры, либо только слова.
        //
        // Использование слов (например, `firstPlayer` и `secondPlayer`) даёт несколько преимуществ перед использованием цифр:
        // - Визуальное различие и читаемость: Имена `firstPlayer` и `secondPlayer` визуально отличаются
            // друг от друга сильнее, чем `player1` и `player2`.
            // Это снижает вероятность их перепутать при быстром просмотре кода.
            // Кроме того, такие имена читаются более естественно, как обычный текст.
        // - Эффективность работы в IDE: При вводе `first...`, IntelliJ IDEA однозначно предложит подсказку `firstPlayer`.
            // При вводе `player...` IDE предложит оба варианта (`player1`, `player2`),
            // что требует дополнительного действия для выбора нужного.
        // - Удобство поиска: Искать по кодовой базе переменную тоже `firstPlayer` может быть проще, чем `player1`
            // (по причине, из предыдущего пункта).

    // Колонки игроков и победителя в `@JoinColumn` названы `player1`, `player2`, `winner`.
        // Для колонок, хранящих внешний ключ, уместно добавлять суффикс `_id`, чтобы было очевидно,
        // что в них хранится идентификатор, а не какая-то другая информация.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "player1",
            foreignKey = @ForeignKey(name = "fk_matches_player1")
    )
    private Player player1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "player2",
            foreignKey = @ForeignKey(name = "fk_matches_player2")
    )
    private Player player2;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "winner",
            foreignKey = @ForeignKey(name = "fk_matches_winner")
    )
    private Player winner;

    protected Match() {
        // Пояснение в этом комментарии избыточно. Комментарий не нужен.
        // for Hibernate
    }

    public Match(Player firstPlayer, Player secondPlayer, Player winner) {
        this.player1 = firstPlayer;
        this.player2 = secondPlayer;
        this.winner = winner;
    }

    public Integer getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "MatchScore{" +
                "id=" + id +
                ", player1='" + player1.getName() + '\'' +
                ", player2='" + player2.getName() + '\'' +
                ", winner='" + winner.getName() + '\'' +
                '}';
    }
}
