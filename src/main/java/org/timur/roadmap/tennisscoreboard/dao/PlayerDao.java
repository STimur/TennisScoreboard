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

    // Нет интерфейса для этого класса.
        // Это нарушение Принципа инверсии зависимостей (Dependency Inversion Principle):
        // Принцип гласит, что модули верхних уровней не должны зависеть от модулей нижних уровней,
        // а также они должны зависеть от абстракций. В данном случае вышестоящие модули (сервисы)
        // напрямую зависят от конкретных реализаций репозиториев, что делает систему жёстко связанной.
        // В небольших проектах это не критично, но в крупных — предпочтительно использовать интерфейсы.

    // Лучше вынести текст HQL запросов в `private static final` константы.
        // Именованная константа делает код более семантически понятным.

    // Текст HQL запроса удобнее читать, когда он логично разбит на строки, даже если он короткий.
        // Для визуального разделения запросов на строки лучше использовать текстовые блоки

    // Ключевые слова в тексте HQL-запроса (`from`, `where` и др.) написаны в нижнем регистре.
        // Хотя это и не влияет на работоспособность, написание ключевых слов SQL/HQL
        // в верхнем регистре (`UPPERCASE`) является общепринятым стандартом.
        // Это значительно улучшает читаемость запросов, так как визуально отделяет
        // синтаксические конструкции языка от имён сущностей и полей.

    // Название именованного параметра тоже можно вынести в константу с понятным названием.

    // TODO: Слой репозиториев должен перехватывать специфичные для технологии исключения
        // и оборачивать их в свои исключения слоя доступа к данным.
        // Это скрывает детали реализации от верхних слоёв и делает их независимыми от деталей реализации репозиториев.
        // Тело каждого метода стоит обернуть в try-catch и отлавливать исключения при работе с БД.
        // Либо добавить механизм автоматической трансляции исключений.
        // Например, это можно сделать создав бин PersistenceExceptionTranslationPostProcessor.
        // Тогда Spring будет сам оборачивать их в исключения из иерархии DataAccessException
        // (например, DataIntegrityViolationException, JpaSystemException и др).

    private final SessionFactory sessionFactory;

    public PlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // TODO: В текущей сигнатуре метод подразумевает возврат абсолютно всех сущностей из таблицы базы данных одним запросом.
        // Это опасный подход с точки зрения производительности и потребления памяти.
        // Если таблица вырастет до значительных размеров (тысячи или миллионы записей), вызов этого метода
        // почти гарантированно приведёт к деградации производительности. Загрузка огромного объёма данных из БД
        // будет чрезвычайно медленной, создавая колоссальную нагрузку и на приложение, и на сервер БД.
        // Что в конечном итоге вызовет `OutOfMemoryError` — попытка загрузить все записи в память приложения приведёт к переполнению кучи (heap).
        // Метод должен принимать параметры `offset` (смещение) и `limit` (количество записей на странице).
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