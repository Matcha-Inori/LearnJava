package com.matcha.app;

import com.matcha.data.ExternalizableObj;
import com.matcha.data.NormalSerializable;
import com.matcha.data.ReplaceAndResolveClass;

import java.io.*;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TestExternalizable
{
    public static void main(String[] args)
    {
        try(
                PipedInputStream pipedInputStream = new PipedInputStream();
                PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
                ObjectOutput objectOutput = new ObjectOutputStream(pipedOutputStream);
                ObjectInput objectInput = new ObjectInputStream(pipedInputStream)
        )
        {
            ReplaceAndResolveClass replaceAndResolveClass = new ReplaceAndResolveClass("为美好的世界献上祝福");
            ExternalizableObj externalizableObj = new ExternalizableObj("Matcha", 23, replaceAndResolveClass,
                    "Bing", "Bang", "Ha");
            NormalSerializable normalSerializable = new NormalSerializable("Randone", "你看不到这段话的", 100,
                    externalizableObj, "ABC", "DEF");
            System.out.println("NormalSerializable is --- " + normalSerializable);
            objectOutput.writeObject(normalSerializable);
            objectOutput.flush();
            NormalSerializable getNormalSerializable = (NormalSerializable) objectInput.readObject();
            System.out.println("NormalSerializable is --- " + getNormalSerializable);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
