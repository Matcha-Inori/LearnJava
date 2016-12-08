package com.matcha.trywithresource;

/**
 * Created by Administrator on 2016/12/8.
 */
public class TestTryCatch
{
    public static void main(String[] args)
    {
        System.out.println(test1());
        System.out.println(test2());
        test3();
    }

    private static String test1()
    {
        try
        {
            throw new Exception("1");
        }
        catch(Exception e)
        {
            return "1-EXCEPTION";
        }
        finally
        {
            return "1-FINALLY";
        }
    }

    private static String test2()
    {
        String result = "2-DEFAULT";
        try
        {
            result = "2";
            throw new Exception("2");
        }
        catch (Exception e)
        {
            result = "2-EXCEPTION";
        }
        finally
        {
            result = "2-FINALLY";
        }
        return result;
    }

    private static void test3()
    {
        for(int i = 0;i < 3;i++)
        {
            try
            {
                System.out.println("3-" + i);
                throw new Exception("3");
            }
            catch (Exception e)
            {
                continue;
            }
            finally
            {
                i++;
            }
        }
    }
}
