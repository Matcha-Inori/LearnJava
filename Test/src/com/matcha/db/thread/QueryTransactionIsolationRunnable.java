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
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(transactionIsolation);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT FID testId, FNAME testName, FNUMBER fNumber FROM T_TEST");
        }
        catch (SQLException e)
        {
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
