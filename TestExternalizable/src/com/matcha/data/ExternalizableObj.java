package com.matcha.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matcha on 16/8/14.
 */
public class ExternalizableObj implements Externalizable
{
    private static final long serialVersionUID = 0xFFFF0001;

    private String name;
    private int age;
    private List<String> own;

    public ExternalizableObj()
    {

    }

    public ExternalizableObj(String name, int age, String...owns)
    {
        this.name = name;
        this.age = age;
        this.own = new ArrayList<String>();
        for(String oneOfOwn : owns)
            this.own.add(oneOfOwn);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(name);
        out.writeInt(age);
        out.writeInt(own.size());
        for(String ownStr : own)
            out.writeObject(ownStr);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.name = (String) in.readObject();
        this.age = in.readInt();
        int size = in.readInt();
        this.own = new ArrayList<String>(size);
        for(int i = 0;i < size;i++)
            this.own.add((String) in.readObject());
    }

    @Override
    public String toString()
    {
        return "ExternalizableObj{" + "name='" + name + '\'' + ", age=" + age + ", own=" + own + '}';
    }
}
