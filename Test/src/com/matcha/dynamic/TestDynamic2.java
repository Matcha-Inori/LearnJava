package com.matcha.dynamic;

import com.matcha.dynamic.app.People;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by Administrator on 2017/3/1.
 */
public class TestDynamic2
{
    public static void main(String[] args)
    {
        try
        {
            People people = new People(20, "Riven");
            people.number = 86;
            MethodType getAgeMethodType = MethodType.methodType(int.class);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle getAgeMethodHandle =
                    lookup.findVirtual(People.class, "getAge", getAgeMethodType).bindTo(people);
            MethodHandle setNumberMethodHandle =
                    lookup.findSetter(People.class, "number", int.class).bindTo(people);
            MethodHandle getNumberMethodHandle =
                    lookup.findGetter(People.class, "number", int.class).bindTo(people);
            System.out.println(String.format("%s's age is %d", people.toString(), getAgeMethodHandle.invoke()));
            System.out.println(String.format("%s's number is %d", people.toString(), getNumberMethodHandle.invoke()));
            setNumberMethodHandle.invokeExact(10);
            System.out.println(String.format("%s's number is %d", people.toString(), getNumberMethodHandle.invoke()));
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }
}
