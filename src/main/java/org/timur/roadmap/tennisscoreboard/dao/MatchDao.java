package org.timur.roadmap.tennisscoreboard.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.timur.roadmap.tennisscoreboard.dto.PageResult;
import org.timur.roadmap.tennisscoreboard.entity.Match;

import java.util.List;

@Repository
public class MatchDao {

    // Нет интерфейса для этого класса.
        // Это нарушение Принципа инверсии зависимостей (Dependency Inversion Principle):
        // Принцип гласит, что модули верхних уровней не должны зависеть от модулей нижних уровней,
        // а также они должны зависеть от абстракций. В данном случае вышестоящие модули (сервисы)
        // напрямую зависят от конкретных реализаций репозиториев, что делает систему жёстко связанной.
        // В небольших проектах это не критично, но в крупных — предпочтительно использовать интерфейсы.

    // Лучше выносить тексты HQL запросов в `private static final` константы и давать им понятные имена.
        // Именованная константа делает код более семантически понятным.
        // И так запросы скомпилируются один раз.
    /*
    Например, так читается лучше, чем построение запроса через StringBuilder с условной логикой внутри метода:
    private static final String SELECT_ALL_HQL = """
            SELECT m
            FROM Match m
            JOIN FETCH m.player1
            JOIN FETCH m.player2
            JOIN FETCH m.winner
            """;
    private static final String FILTER_BY_PLAYER_NAME_HQL = """
            WHERE m.player1.name = :playerName
               OR m.player2.name = :playerName
            """;
    private static final String ORDER_BY_ID_HQL = """
            ORDER BY m.id DESC
            """;

    private static final String FIND_ALL_HQL =
            SELECT_ALL_HQL +
            ORDER_BY_ID_HQL;

    private static final String FIND_ALL_BY_PLAYER_NAME_HQL =
            SELECT_ALL_HQL +
            FILTER_BY_PLAYER_NAME_HQL +
            ORDER_BY_ID_HQL;
     */

    // Ключевые слова в тексте HQL-запросов (`from`, `where` и др.) написаны в нижнем регистре.
        // Хотя это и не влияет на работоспособность, написание ключевых слов SQL/HQL
        // в верхнем регистре (`UPPERCASE`) является общепринятым стандартом.
        // Это значительно улучшает читаемость запросов, так как визуально отделяет
        // синтаксические конструкции языка от имён сущностей и полей.

    // TODO: Слой репозиториев должен перехватывать специфичные для технологии исключения
        // и оборачивать их в свои исключения слоя доступа к данным.
        // Это скрывает детали реализации от верхних слоёв и делает их независимыми от деталей реализации репозиториев.
        // Тело каждого метода стоит обернуть в try-catch и отлавливать исключения при работе с БД.
        // Либо добавить механизм автоматической трансляции исключений.
        // Например, это можно сделать создав бин PersistenceExceptionTranslationPostProcessor.
        // Тогда Spring будет сам оборачивать их в исключения из иерархии DataAccessException
        // (например, DataIntegrityViolationException, JpaSystemException и др).

    // Размер страницы по умолчанию более уместно хранить в контроллере, так как в идеале он должен приходить с фронтенда.
        // Репозиторий вообще не должен знать о таком понятии — ему достаточно получать параметр limit в метод выборки.
    private static final int PAGE_SIZE = 10;

    private final SessionFactory sessionFactory;

    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // TODO: В текущей сигнатуре метод подразумевает возврат абсолютно всех сущностей из таблицы базы данных одним запросом.
        // Это опасный подход с точки зрения производительности и потребления памяти.
        // Если таблица вырастет до значительных размеров (тысячи или миллионы записей), вызов этого метода
        // почти гарантированно приведёт к деградации производительности. Загрузка огромного объёма данных из БД
        // будет чрезвычайно медленной, создавая колоссальную нагрузку и на приложение, и на сервер БД.
        // Что в конечном итоге вызовет `OutOfMemoryError` — попытка загрузить все записи в память приложения приведёт к переполнению кучи (heap).
        // Метод должен принимать параметры `offset` (смещение) и `limit` (количество записей на странице).
    public List<Match> findAll() {
        Session session = sessionFactory.getCurrentSession();

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

    public void save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(match);
    }

    // Лучше иметь разные методы для получения выборки матчей с фильтром по имени и без него,
        // чем собирать эту логику в одном методе. Если правила фильтрации поменяются,
        // то нужно будет изменить/дописать только некоторые методы, оставив логику выборки без фильтра без изменений.
    // А также стоит разделить получение матчей и подсчёт их количества на разные методы,
        // чтобы каждый метод занимался чем-то одним и соблюдал SRP на уровне метода.
    public PageResult<Match> findFinishedMatches(int page, String playerName) {
        Session session = sessionFactory.getCurrentSession();

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