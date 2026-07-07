package org.timur.roadmap.tennisscoreboard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchDao {

    private final SessionFactory sessionFactory;

    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Match> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                            select m
                            from Match m
                            join fetch m.player1
                            join fetch m.player2
                            join fetch m.winner
                            """,
                    Match.class
            ).getResultList();
        }
    }
}