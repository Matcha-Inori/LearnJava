package com.matcha.app;

import com.matcha.data.*;
import com.sun.xml.internal.xsom.impl.WildcardImpl;

import java.io.*;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TestExternalizable
{
    public static void main(String[] args)
    {
        byte[] bytes = writeSerializable();
        readSerializable(bytes);
    }

    public static byte[] writeSerializable()
    {
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
                )
        {
            ReplaceAndResolveClass replaceAndResolveClass = new ReplaceAndResolveClass("为美好的世界献上祝福");
            ExternalizableObj externalizableObj = new ExternalizableObj("Matcha", 23, replaceAndResolveClass,
                    "Bing", "Bang", "Ha");
            OtherNormalSerializable otherNormalSerializable =
                    new OtherNormalSerializable("这个不会序列化", "own1", "own2", "own3", "own3");
            SubNormalSerializable subNormalSerializable = new SubNormalSerializable("Randone", "你看不到这段话的", 100,
                    externalizableObj, otherNormalSerializable, "ABC", "DEF");
            subNormalSerializable.add("TEST---1");
            subNormalSerializable.add("TEST---2");
            subNormalSerializable.add("TEST---3");
            subNormalSerializable.add("TEST---4");
            System.out.println(subNormalSerializable.pop());
            System.out.println(subNormalSerializable.pop());
            System.out.println(subNormalSerializable.pop());
            System.out.println("SubNormalSerializable is --- " + subNormalSerializable);
            objectOutputStream.writeObject(subNormalSerializable);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Object readSerializable(byte[] bytes)
    {
        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
                )
        {
            SubNormalSerializable getSubNormalSerializable = (SubNormalSerializable) objectInputStream.readObject();
            System.out.println("SubNormalSerializable is --- " + getSubNormalSerializable);
            return getSubNormalSerializable;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
