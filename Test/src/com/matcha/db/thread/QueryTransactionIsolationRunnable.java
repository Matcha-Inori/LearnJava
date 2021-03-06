package com.matcha.db.thread;

import com.matcha.db.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/6/30.
 */
public class QueryTransactionIsolationRunnable implements Runnable
{
    private ConnectionPool connectionPool;
    private int transactionIsolation;

    public QueryTransactionIsolationRunnable(ConnectionPool connectionPool, int transactionIsolation)
    {
        this.connectionPool = connectionPool;
        this.transactionIsolation = transactionIsolation;
    }

    @Override
    public void run()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            connection = connectionPool.getConnection();
            connection.setTransactionIsolation(transactionIsolation);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT FID testId, FNAME testName, FNUMBER testNumber FROM T_TEST");
            Thread currentThread = Thread.currentThread();
            long testId;
            String testName;
            String testNumber;
            String info;
            while(resultSet.next())
            {
                testId = resultSet.getLong("testId");
                testName = resultSet.getString("testName");
                testNumber = resultSet.getString("testNumber");
                info = String.format("%s : %s-%s-%s", currentThread.getName(), testId, testName, testNumber);
                System.out.println(info);
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            try
            {
                if(connection != null)
                    connection.rollback();
            }
            catch (SQLException rollbackException)
            {
            }
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                    resultSet.close();
            }
            catch (SQLException e)
            {
            }

            try
            {
                if(statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
            }

            if(connection != null)
                connectionPool.releaseConnection(connection);
        }
    }
}
