package com.matcha.unsafe;

import com.matcha.unsafe.app.InnerObject;
import com.matcha.unsafe.app.TestUnsafeObject;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by rd_xidong_ren on 2016/7/29.
 */
public class TestUnsafe
{
    private static final Unsafe unsafe;
    private static final long arrayOffset;
    private static final long arrayIndexShift;
    private static final long fieldOffset;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            arrayOffset = unsafe.arrayBaseOffset(InnerObject[].class);
            arrayIndexShift = unsafe.arrayIndexScale(InnerObject[].class);
            fieldOffset = unsafe.objectFieldOffset(TestUnsafeObject.class.getDeclaredField("innerObjects"));
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        TestUnsafeObject testUnsafeObject = new TestUnsafeObject();
        System.out.println("unsafe start");
        long unsafeStartTime = System.currentTimeMillis();
        Object innerObjects = unsafe.getObject(testUnsafeObject, fieldOffset);
        long index = arrayOffset;
        Object innerObject1 = unsafe.getObject(innerObjects, index);
        index += arrayIndexShift;
        Object innerObject2 = unsafe.getObject(innerObjects, index);
        index += arrayIndexShift;
        Object innerObject3 = unsafe.getObject(innerObjects, index);
        long unsafeFinishTime = System.currentTimeMillis();
        System.out.println("unsafe finish");
        System.out.println(innerObject1);
        System.out.println(innerObject2);
        System.out.println(innerObject3);
        System.out.println("unsafe use " + (unsafeFinishTime - unsafeStartTime) + " ms");

        System.out.println("normal start");
        long normalStartTime = System.currentTimeMillis();
        InnerObject[] innerObjectArray = testUnsafeObject.innerObjects;
        int indexI = 0;
        innerObject1 = innerObjectArray[indexI];
        indexI++;
        innerObject2 = innerObjectArray[indexI];
        indexI++;
        innerObject3 = innerObjectArray[indexI];
        long normalFinishTime = System.currentTimeMillis();
        System.out.println("normal finish");
        System.out.println(innerObject1);
        System.out.println(innerObject2);
        System.out.println(innerObject3);
        System.out.println("normal use " + (normalFinishTime - normalStartTime) + " ms");
    }
}
