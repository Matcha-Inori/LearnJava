package com.matcha.db.thread;

import com.matcha.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by Matcha on 2017/6/30.
 */
public class InsertTransactionIsolationRunnable implements Runnable
{
    private ConnectionPool connectionPool;
    private int transactionIsolation;
    private int insertCount;
    private long startId;

    private Random random;

    public InsertTransactionIsolationRunnable(ConnectionPool connectionPool,
                                              int transactionIsolation,
                                              int insertCount,
                                              long startId)
    {
        this.connectionPool = connectionPool;
        this.transactionIsolation = transactionIsolation;
        this.insertCount = insertCount;
        this.startId = startId;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            String sql = "INSERT INTO T_TEST(FID, FNAME, FNUMBER) VALUES (?, ?, ?)";
            connection = connectionPool.getConnection();
            connection.setTransactionIsolation(transactionIsolation);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            long testId;
            String testName;
            String testNumber;
            for(int index = 0;index < insertCount;index++)
            {
                testId = startId + index;
                testName = randomString();
                testNumber = randomString();
                preparedStatement.setLong(1, testId);
                preparedStatement.setString(2, testName);
                preparedStatement.setString(3, testNumber);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
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
                if(preparedStatement != null)
                    preparedStatement.close();
            }
            catch (SQLException e)
            {
            }

            if(connection != null)
                connectionPool.releaseConnection(connection);
        }
    }

    private String randomString()
    {
        int charLength = this.random.nextInt(30) + 1;
        int offset;
        char[] chars = new char[charLength];
        for(int index = 0;index < charLength;index++)
        {
            offset = this.random.nextInt(25);
            chars[index] = (char) ('A' + offset);
        }
        return new String(chars);
    }
}
