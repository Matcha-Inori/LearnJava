package com.matcha.dynamic;

import com.matcha.dynamic.app.MethodObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by Matcha on 2016/11/28.
 */
public class TestDynamic
{
    public static void main(String[] args)
    {
        try
        {
            MethodObject methodObject = new MethodObject();
            MethodType method1Type = MethodType.methodType(void.class, new Class[]{String.class, int.class});
            MethodType method2Type = MethodType.methodType(String.class);
            MethodHandle method1Handle =
                    MethodHandles.lookup().findVirtual(MethodObject.class, "method1", method1Type)
                    .bindTo(methodObject);
            MethodHandle method2Handle =
                    MethodHandles.lookup().findVirtual(MethodObject.class, "method2", method2Type)
                    .bindTo(methodObject);
            method1Handle.invokeExact("str", 10);
            String returnStr = (String) method2Handle.invokeExact();
            System.out.println("returnStr is " + returnStr);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }
}
