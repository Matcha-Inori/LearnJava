package com.matcha.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/15.
 */
public class UnsafeUtil
{
    private static final Unsafe unsafe;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static long sizeOf(Object o)
    {
        Class<?> classO = o.getClass();
        Set<Field> fileds = new HashSet();
        while(classO != Object.class)
        {
            for(Field field : classO.getDeclaredFields())
                fileds.add(field);
            classO = classO.getSuperclass();
        }

        long maxSize = 0;
        long offset = 0;
        for(Field field : fileds)
        {
            offset = unsafe.objectFieldOffset(field);
            if(offset > maxSize)
                maxSize = offset;
        }
        return ((maxSize / 8) + 1) * 8;
    }

    public static long toAddress(Object obj)
    {
        Object[] objects = new Object[]{obj};
        int arrayBaseOffset = unsafe.arrayBaseOffset(Object[].class);
        return normalize(unsafe.getInt(objects, arrayBaseOffset));
    }

    public static Object fromAddress(long address)
    {
        Object[] objects = new Object[]{null};
        int arrayBaseOffset = unsafe.arrayBaseOffset(Object[].class);
        unsafe.putLong(objects, arrayBaseOffset, address);
        return objects[0];
    }

    public static long normalize(int value)
    {
        if(value >= 0) return value;
        return (~0L >>> 32) & value;
    }
}
