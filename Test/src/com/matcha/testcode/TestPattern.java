package com.matcha.testcode;

/**
 * Created by Matcha on 2016/11/29.
 */
public class TestPattern<T>
{
    private T value;

    public TestPattern(T value)
    {
        this.value = value;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
}
