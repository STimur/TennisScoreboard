package org.timur.roadmap.tennisscoreboard.infrastructure;

@FunctionalInterface
public interface TransactionAction<T> {

    // Интерфейс для действия, возвращающего результат есть, а для возвращающего void — нет.
        // Стоит использовать один подход — или создать второй интерфейс (например VoidTransactionAction)
        // или для возврата результата тоже использовать стандартный интерфейс Java — Supplier.

    T execute();
}
