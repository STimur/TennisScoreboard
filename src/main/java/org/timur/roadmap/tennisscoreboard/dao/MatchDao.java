package org.timur.roadmap.tennisscoreboard.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.timur.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.timur.roadmap.tennisscoreboard.dto.PageResult;
import org.timur.roadmap.tennisscoreboard.entity.Match;

import java.util.List;

@Repository
public class MatchDao {

    private static final int PAGE_SIZE = 10;

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

    public void save(OngoingMatch match) {

    }

    public PageResult<Match> findFinishedMatches(int page, String playerName) {
        try (Session session = sessionFactory.openSession()) {

            StringBuilder hql = new StringBuilder("""
                    from Match m
                    join fetch m.player1
                    join fetch m.player2
                    join fetch m.winner
                    """);

            StringBuilder countHql = new StringBuilder("""
                    select count(m)
                    from Match m
                    """);

            if (playerName != null && !playerName.isBlank()) {

                hql.append("""
                        where m.player1.name = :playerName
                           or m.player2.name = :playerName
                        """);

                countHql.append("""
                        where m.player1.name = :playerName
                           or m.player2.name = :playerName
                        """);
            }

            hql.append(" order by m.id desc");

            Query<Match> query = session.createQuery(hql.toString(), Match.class);
            Query<Long> countQuery = session.createQuery(countHql.toString(), Long.class);

            if (playerName != null && !playerName.isBlank()) {
                query.setParameter("playerName", playerName);
                countQuery.setParameter("playerName", playerName);
            }

            query.setFirstResult(page * PAGE_SIZE);
            query.setMaxResults(PAGE_SIZE);

            List<Match> matches = query.getResultList();

            long totalItems = countQuery.getSingleResult();

            int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

            return new PageResult<>(
                    matches,
                    page,
                    totalPages,
                    totalItems
            );
        }
    }
}