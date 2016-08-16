package com.matcha.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Matcha on 16/8/17.
 */
public class OtherNormalSerializable implements Serializable
{
    private static final long serialVersionUID = 0xFF01;

    private String info;
    private String[] own;

    public OtherNormalSerializable(String info, String... owns)
    {
        this.info = info;
        this.own = new String[owns.length];
        System.arraycopy(owns, 0, this.own, 0, owns.length);
    }

    private void writeObject(ObjectOutputStream objectOutputStream)
    {
        System.out.println("OtherNormalSerializable writeObject is in !!!");
        try
        {
            objectOutputStream.writeObject(own);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream)
    {
        System.out.println("OtherNormalSerializable readObject is in !!!");
        try
        {
            this.own = (String[]) objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString()
    {
        return "OtherNormalSerializable{" +
                "info='" + info + '\'' +
                ", own=" + Arrays.toString(own) +
                '}';
    }
}
