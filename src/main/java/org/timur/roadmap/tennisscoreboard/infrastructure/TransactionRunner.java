package org.timur.roadmap.tennisscoreboard.infrastructure;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.timur.roadmap.tennisscoreboard.exception.DataAccessException;

@Component
public class TransactionRunner {

    // TODO: Трансляцию исключений от БД в DatabaseException лучше перенести в слой репозиториев,
        // так как это их ответственность. Тогда как работа с транзакциями — ответственность слоя сервисов.
        // Также TransactionManager трансляцией исключений нарушает принцип единственной ответственности (SRP).
        // Менеджер транзакций не должен заниматься маппингом ошибок.
        // Лучше в TransactionManager после отката транзакции прокидывать оригинальное исключение без оборачивания.

    // Комментарии, которые не несут значимой смысловой нагрузки или
        // просто описывают работу методов, не нужны. Стоит удалять их перед коммитом.

    private final SessionFactory sessionFactory;

    public TransactionRunner(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Метод для операций, которые возвращают результат (например, получить DTO)
    public <T> T runInTransaction(TransactionAction<T> action) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        // Переменная isExistingTransaction всегда используется с отрицанием.
            // Лучше вместо неё создать такую:
            // boolean isNewTransaction = transaction.getStatus() == TransactionStatus.NOT_ACTIVE;
            // или boolean isNewTransaction = !transaction.isActive();
        // Проверяем, активна ли транзакция в текущем потоке (была ли она открыта выше по стеку)
        boolean isExistingTransaction = transaction.isActive();

        try {
            if (!isExistingTransaction) {
                // Только если это САМАЯ ВНЕШНЯЯ транзакция — стартуем её
                transaction.begin();
            }

            // Выполняем логику (вложенный сервис выполнится здесь)
            T result = action.execute();

            if (!isExistingTransaction) {
                // Фиксируем транзакцию ТОЛЬКО на самом верхнем уровне
                transaction.commit();
            }

            return result;
        } catch (Throwable ex) {
            // Если упала ошибка (неважно, на каком уровне), делаем rollback
            if (transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rbe) {
                    ex.addSuppressed(rbe);
                }
            }
            throw new DataAccessException(ex);
        } finally {
            // Закрываем сессию ТОЛЬКО если мы её открывали (на внешнем уровне).
            // При context="thread" и откате/коммите сессия может закрыться сама,
            // но проверка предотвращает повторное закрытие уже мертвой сессии.
            if (!isExistingTransaction && session.isOpen()) {

                // TODO: TransactionRunner не должен управлять жизненным циклом сессии.
                    // Его ответственность — транзакции.
                session.close();
            }
        }
    }

    // Перегруженный метод для операций void (например, сохранить матч)
    public void runInTransaction(Runnable action) {
        runInTransaction(() -> {
            action.run();
            return null;
        });
    }
}
