package com.matcha.app;

import com.matcha.data.ExternalizableObj;

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
            ExternalizableObj externalizableObj = new ExternalizableObj("Matcha", 23, "Bing", "Bang", "Ha");
            System.out.println("ExternalizableObj is --- " + externalizableObj);
            objectOutput.writeObject(externalizableObj);
            objectOutput.flush();
            ExternalizableObj getExternalizableObj = (ExternalizableObj) objectInput.readObject();
            System.out.println("getExternalizableObj is --- " + getExternalizableObj);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
