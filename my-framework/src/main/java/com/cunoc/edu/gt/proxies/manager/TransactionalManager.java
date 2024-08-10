package com.cunoc.edu.gt.proxies.manager;

import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Getter
public class TransactionalManager {

    public void beginTransaction() throws SQLException {
        Logger.getLogger("TransactionalManager").info("Begin transaction");
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        Logger.getLogger("TransactionalManager").info("Commit transaction");
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        Logger.getLogger("TransactionalManager").info("Rollback transaction");
        connection.rollback();
        connection.setAutoCommit(true);
    }

    private TransactionalManager() {
    }

    public static TransactionalManager getInstance(Connection connection) {
        TransactionalManager.connection = connection;
        if (instance == null) {
            instance = new TransactionalManager();
        }
        return instance;
    }

    private static Connection connection;
    private static TransactionalManager instance;
}