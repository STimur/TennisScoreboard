package org.timur.roadmap.tennisscoreboard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}