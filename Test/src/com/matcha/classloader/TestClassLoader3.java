package com.matcha.classloader;

/**
 * Created by Matcha on 2017/4/25.
 */
public class TestClassLoader3
{
    public static void main(String[] args)
    {
        try
        {
            OwnClassLoader2 ownClassLoader2 = new OwnClassLoader2();
            Class<?> newObjectClass = ownClassLoader2.loadClass(Object.class.getName());
            newObjectClass.newInstance();
        }
        catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
