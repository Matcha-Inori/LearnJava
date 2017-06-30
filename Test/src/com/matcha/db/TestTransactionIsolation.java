package com.matcha.db;

import com.matcha.db.thread.InsertTransactionIsolationRunnable;
import com.matcha.db.thread.QueryTransactionIsolationRunnable;
import com.matcha.db.thread.UpdateTransactionIsolationRunnable;

import java.sql.Connection;

/**
 * Created by Matcha on 2017/6/30.
 */
public class TestTransactionIsolation
{
    public static final String dbConnectionUrl;
    public static final String dbUser;
    public static final String dbPassword;

    static
    {
        dbConnectionUrl = "jdbc:mysql://192.168.56.8:3306/matchatest";
        dbUser = "matcha";
        dbPassword = "matcha";
    }

    public static void main(String[] args)
    {
        ConnectionPoolBuilder connectionPoolBuilder = new ConnectionPoolBuilder();
        connectionPoolBuilder.setDbConnectionUrl(dbConnectionUrl)
                            .setUserName(dbUser).setPassword(dbPassword);
        ConnectionPool connectionPool = connectionPoolBuilder.build();
        int transactionIsolation = Connection.TRANSACTION_READ_UNCOMMITTED;
        Thread threadInsert = new Thread(
                new InsertTransactionIsolationRunnable(
                        connectionPool,
                        transactionIsolation,
                        5,
                        10L),
                "Matcha - Insert"
        );
        Thread threadQuery = new Thread(
                new QueryTransactionIsolationRunnable(
                        connectionPool,
                        Connection.TRANSACTION_READ_COMMITTED
                ),
                "Matcha - Query"
        );
        Thread updateQuery = new Thread(
                new UpdateTransactionIsolationRunnable(
                        connectionPool,
                        transactionIsolation,
                        2,
                        "Riven"
                ),
                "Matcha - Update"
        );
        threadInsert.start();
        threadQuery.start();
        updateQuery.start();
    }
}
