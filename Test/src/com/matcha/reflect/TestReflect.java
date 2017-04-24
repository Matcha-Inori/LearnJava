package com.matcha.reflect;

import com.matcha.reflect.app.People;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/24.
 */
public class TestReflect
{
    public static void main(String[] args)
    {
        try
        {
            Constructor<People> constructor = People.class.getDeclaredConstructor(String.class, int.class);
            People peopleRiven = constructor.newInstance("Riven", 22);
            Method singMethod = People.class.getDeclaredMethod("sing", String.class);
            for(int index = 0;index < 30;index++)
                singMethod.invoke(peopleRiven, "ha ha ha");
            Method sleepMethod = People.class.getDeclaredMethod("sleep");
            sleepMethod.setAccessible(true);
            sleepMethod.invoke(sleepMethod);
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
