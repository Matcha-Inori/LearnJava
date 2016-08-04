package com.matcha.unsafe;

import com.matcha.unsafe.app.InnerObject;
import com.matcha.unsafe.app.InnerObjectWrapper;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rd_xidong_ren on 2016/7/30.
 */
public class TestUnsafe2
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

    public static void main(String[] args)
    {
        InnerObjectWrapper innerObjectWrapper = new InnerObjectWrapper(new InnerObject("AAA", 20));
        long pointer = unsafe.allocateMemory(1024);
        try
        {
            long insertPosistion = pointer + 100;
            long innerObjectWrapperSize = sizeOf(innerObjectWrapper);
            long innerObjectWrapperAddress = toAddress(innerObjectWrapper);
            unsafe.copyMemory(innerObjectWrapperAddress, insertPosistion, innerObjectWrapperSize);
            Object obj = fromAddress(insertPosistion);
            System.out.println(obj);
        }
        finally
        {
            unsafe.freeMemory(pointer);
        }
    }

    private static long sizeOf(Object o)
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

    private static long toAddress(Object obj)
    {
        Object[] objects = new Object[]{obj};
        int arrayBaseOffset = unsafe.arrayBaseOffset(Object[].class);
        return normalize(unsafe.getInt(objects, arrayBaseOffset));
    }

    private static Object fromAddress(long address)
    {
        Object[] objects = new Object[]{null};
        int arrayBaseOffset = unsafe.arrayBaseOffset(Object[].class);
        unsafe.putLong(objects, arrayBaseOffset, address);
        return objects[0];
    }

    private static long normalize(int value)
    {
        if(value >= 0) return value;
        return (~0L >>> 32) & value;
    }
}
