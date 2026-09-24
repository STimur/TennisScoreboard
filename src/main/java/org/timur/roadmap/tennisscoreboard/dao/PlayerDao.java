package org.timur.roadmap.tennisscoreboard.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.timur.roadmap.tennisscoreboard.entity.Player;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayerDao {

    private final SessionFactory sessionFactory;

    public PlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Player> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery(
                        "from Player order by name",
                        Player.class)
                .getResultList();
    }

    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("""
                        from Player
                        where name = :name
                        """, Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    public Player save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
        return player;
    }
}