package com.matcha.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 16/8/14.
 */
public class ExternalizableObj implements Externalizable
{
    private static final long serialVersionUID = 0xFF01;

    private String name;
    private int age;
    private ReplaceAndResolveClass replaceAndResolveClass;
    private List<String> own;

    public ExternalizableObj()
    {
    }

    public ExternalizableObj(String name, int age, ReplaceAndResolveClass replaceAndResolveClass, String...owns)
    {
        this.name = name;
        this.age = age;
        this.replaceAndResolveClass = replaceAndResolveClass;
        this.own = new ArrayList<String>();
        for(String oneOfOwn : owns)
            this.own.add(oneOfOwn);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(name);
        out.writeInt(age);
        out.writeObject(replaceAndResolveClass);
        out.writeInt(own.size());
        for(String ownStr : own)
            out.writeObject(ownStr);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.name = (String) in.readObject();
        this.age = in.readInt();
//        this.replaceAndResolveClass = (ReplaceAndResolveClass) in.readObject();
        this.replaceAndResolveClass = new ReplaceAndResolveClass((String) in.readObject());
        int size = in.readInt();
        this.own = new ArrayList<String>(size);
        for(int i = 0;i < size;i++)
            this.own.add((String) in.readObject());
    }

    @Override
    public String toString()
    {
        return "ExternalizableObj{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", replaceAndResolveClass=" + replaceAndResolveClass +
                ", own=" + own +
                '}';
    }
}
