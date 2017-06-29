package com.matcha.db;

import java.sql.*;

/**
 * Created by Matcha on 2017/6/30.
 */
public class TestDB
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
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        String insertSql = "INSERT INTO T_TEST(FID, FNAME, FNUMBER) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = DriverManager.getConnection(dbConnectionUrl, dbUser, dbPassword);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSql);
            Savepoint savepoint = connection.setSavepoint();
            preparedStatement.setLong(1, 1L);
            preparedStatement.setString(2, "Riven");
            preparedStatement.setString(3, "25");
            preparedStatement.addBatch();
            preparedStatement.execute();
//            if(true)
//                throw new RuntimeException("sss");
            connection.commit();
        }
        catch (Throwable e)
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

            try
            {
                if(connection != null)
                    connection.close();
            }
            catch (SQLException e)
            {
            }
        }
    }
}
