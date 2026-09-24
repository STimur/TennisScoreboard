package org.timur.roadmap.tennisscoreboard.infrastructure;

@FunctionalInterface
public interface TransactionAction<T> {
    T execute();
}
