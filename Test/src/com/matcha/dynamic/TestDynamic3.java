package com.matcha.dynamic;

import com.matcha.dynamic.app.MethodObject2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by Administrator on 2017/4/25.
 */
public class TestDynamic3
{
    public static void main(String[] args)
    {
        try
        {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
//            MethodType setMethodType = MethodType.methodType(void.class, String.class);
            MethodType getMethodType = MethodType.methodType(String.class);
//            MethodHandle setStrMethodHandle = lookup.findVirtual(MethodObject2.class, "setStr", setMethodType);
            MethodHandle getStrMethodHandle = lookup.findVirtual(MethodObject2.class, "getStr", getMethodType);
            MethodObject2 methodObject2 = new MethodObject2("Riven");
//            setStrMethodHandle = setStrMethodHandle.bindTo(methodObject2);
//            setStrMethodHandle.invoke("Hi Five!");
            getStrMethodHandle = getStrMethodHandle.bindTo(methodObject2);
            String str = (String) getStrMethodHandle.invoke();
            System.out.println(str);

            MethodType printMethodType = MethodType.methodType(void.class, String.class);
            MethodHandle printMethodHandle = lookup.findVirtual(MethodObject2.class, "print", printMethodType);
            printMethodHandle = printMethodHandle.bindTo(methodObject2);
            printMethodHandle.invoke("为美好的世界献上祝福");
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
