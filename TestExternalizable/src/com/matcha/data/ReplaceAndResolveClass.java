package com.matcha.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Matcha on 16/8/15.
 */
public class ReplaceAndResolveClass implements Externalizable
{
    private static final long serialVersionUID = 0xFF01;

    private String info;

    public ReplaceAndResolveClass(String info)
    {
        this.info = info;
    }

    private Object writeReplace()
    {
        System.out.println(ReplaceAndResolveClass.class.getName() + " writeReplace() in !!");
        return this.info;
    }

    private Object readResolve()
    {
        System.out.println(ReplaceAndResolveClass.class.getName() + " readResolve() in !!");
        return this.info;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(info);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.info = (String) in.readObject();
    }

    @Override
    public String toString()
    {
        return "ReplaceAndResolveClass{" +
                "info='" + info + '\'' +
                '}';
    }
}
