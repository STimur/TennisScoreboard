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
@Table(name = "matches")
public class Match {

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
        return "Match{" +
                "id=" + id +
                ", player1='" + player1.getName() + '\'' +
                ", player2='" + player2.getName() + '\'' +
                ", winner='" + winner.getName() + '\'' +
                '}';
    }
}
