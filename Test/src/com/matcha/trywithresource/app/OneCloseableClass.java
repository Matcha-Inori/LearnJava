package com.matcha.trywithresource.app;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/7.
 */
public class OneCloseableClass implements Closeable
{
    private static AtomicInteger nextNumber;

    static
    {
        nextNumber = new AtomicInteger(0);
    }

    private int number;

    public OneCloseableClass()
    {
        number = nextNumber.getAndIncrement();
    }

    @Override
    public void close() throws IOException
    {
        System.out.println("OneCloseableClass - " + this + " has closed");
        throw new IOException(this.toString());
    }

    public void print()
    {
        System.out.println("matcha - " + number + " print");
    }

    @Override
    public String toString()
    {
        return "OneCloseableClass{" +
                "number=" + number +
                '}';
    }
}
