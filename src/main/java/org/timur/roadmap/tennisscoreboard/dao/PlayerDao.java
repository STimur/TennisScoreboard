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
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "from Player order by name",
                    Player.class
            ).getResultList();
        }
    }

    public Optional<Player> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("""
                from Player
                where name = :name
                """, Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    public Player save(Player player) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(player);

            transaction.commit();

            return player;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }
}