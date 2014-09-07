package com.rudenkoInc.eshop.dao.externalTxDao.impl;


import com.mysql.jdbc.Driver;
import com.rudenkoInc.eshop.dao.WorkToDoInterface.UnitOfWork;
import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;
import com.rudenkoInc.eshop.dao.externalTxDao.TransactionsManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class TransactionManagerExternalTxImpl extends BaseDataSource implements TransactionsManager {
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/eshop?user=admin&password=pdtcthcndjcj52";
    public ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> workToDo) throws E {
        System.out.println("inside TransactionManagerExternalTxImpl.doInTransaction:");
        Connection conn = null;
        T result = null;
        try {
        DriverManager.registerDriver(new Driver());
        conn = DriverManager.getConnection(JDBC_URL);
        System.out.println("Connection: " + conn);
        conn.setAutoCommit(false);
        connectionHolder.set(conn);
            result = workToDo.doInTx();
            conn.commit();
            return result;
        } catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException | NullPointerException e1) {
                e1.printStackTrace();
            }
        } finally {
            JdbcUtils.closeQuietly(conn);
            connectionHolder.remove();
        }
        return result;
    }

    @Override
    public Connection getConnection() throws SQLException {
        System.out.println("inside TransactionManagerExternalTxImpl.getConnection():");
        return connectionHolder.get();
    }
}
