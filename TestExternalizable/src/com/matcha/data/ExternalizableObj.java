package com.matcha.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Matcha on 16/8/14.
 */
public class ExternalizableObj implements Externalizable
{
    private static final String[] forbid;

    static
    {
        forbid = new String[]{
            "",
            "",
            ""
        };
    }

    private String name;
    private int age;
    private List<String> own;

    public ExternalizableObj(String name, int age, String...owns)
    {
        this.name = name;
        this.age = age;
        own = new ArrayList<String>();
        for(String oneOfOwn : owns)
            own.add(oneOfOwn);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(name);
        out.writeInt(age);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {

    }
}
