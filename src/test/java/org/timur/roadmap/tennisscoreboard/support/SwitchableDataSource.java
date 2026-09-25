package org.timur.roadmap.tennisscoreboard.support;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.timur.roadmap.tennisscoreboard.exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SwitchableDataSource extends AbstractDataSource {

    private final DataSource delegate;
    private volatile boolean failing;

    public SwitchableDataSource(DataSource delegate) {
        this.delegate = delegate;
    }

    public void fail() {
        failing = true;
    }

    public void recover() {
        failing = false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (failing) {
            throw new DataAccessException(null);
        }

        return delegate.getConnection();
    }

    @Override
    public Connection getConnection(
            String username,
            String password
    ) throws SQLException {
        if (failing) {
            throw new DataAccessException(null);
        }

        return delegate.getConnection(username, password);
    }
}