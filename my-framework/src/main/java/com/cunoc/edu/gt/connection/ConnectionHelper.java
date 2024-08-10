package com.cunoc.edu.gt.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * This class is used to close the connection, prepared statement and result set
 *
 * @Author: Augusto Vicente
 */
public class ConnectionHelper {

    /**
     * Close result set
     *
     * @param rs result set
     */
    public static void closeResultSet(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error closing result set: %s", e.getMessage()));
            }
        }
    }

    /**
     * Close prepared statement
     *
     * @param ps prepared statement
     */
    public static void closePreparedStatement(PreparedStatement ps){
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error closing prepared statement: %s", e.getMessage()));
            }
        }
    }

    /**
     * Close connection
     *
     * @param con connection
     */
    public static void closeConnection(Connection con){
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error closing connection: %s", e.getMessage()));
            }
        }
    }

    /**
     * Rollback connection
     *
     * @param con connection
     */
    public static void rollback(Connection con){
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error rolling back connection: %s", e.getMessage()));
            }
        }
    }

    /**
     * Commit connection
     *
     * @param con connection
     */
    public static void commit(Connection con){
        if (con != null) {
            try {
                con.commit();
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error committing connection: %s", e.getMessage()));
            }
        }
    }

    /**
     * Set auto commit
     *
     * @param con connection
     * @param autoCommit auto commit
     */
    public static void setAutoCommit(Connection con, boolean autoCommit){
        if (con != null) {
            try {
                con.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error setting auto commit: %s", e.getMessage()));
            }
        }
    }

    /**
     * Rollback connection
     *
     * @param con connection
     * @param savepoint savepoint
     */
    public static void rollback(Connection con, String savepoint){
        if (con != null) {
            try {
                con.rollback(con.setSavepoint(savepoint));
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error rolling back connection: %s", e.getMessage()));
            }
        }
    }

    /**
     * Commit connection
     *
     * @param con connection
     * @param savepoint savepoint
     */
    public static void commit(Connection con, String savepoint){
        if (con != null) {
            try {
                con.commit();
            } catch (SQLException e) {
                Logger.getLogger("ConnectionHelper").severe(String.format("Error committing connection: %s", e.getMessage()));
            }
        }
    }

    //Singleton pattern
    private ConnectionHelper() {
    }

    public static ConnectionHelper getInstance() {
        if (instance == null) {
            instance = new ConnectionHelper();
        }
        return instance;
    }

    private static ConnectionHelper instance;
}