package com.matcha.db.thread;

import com.matcha.db.ConnectionPool;

import java.sql.*;
import java.util.Random;

/**
 * Created by Matcha on 2017/7/1.
 */
public class UpdateTransactionIsolationRunnable implements Runnable
{
    private ConnectionPool connectionPool;
    private int transactionIsolation;
    private long updateDataId;
    private String updateName;

    public UpdateTransactionIsolationRunnable(ConnectionPool connectionPool, int transactionIsolation, long updateDataId, String updateName)
    {
        this.connectionPool = connectionPool;
        this.transactionIsolation = transactionIsolation;
        this.updateDataId = updateDataId;
        this.updateName = updateName;
    }

    @Override
    public void run()
    {
        Connection connection = null;
        Statement statement = null;
        try
        {
            String sql = String.format("UPDATE T_TEST SET FNAME = '%s' WHERE FID = %d", updateName, updateDataId);
            connection = connectionPool.getConnection();
            connection.setTransactionIsolation(transactionIsolation);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(sql);
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
