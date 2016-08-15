package com.matcha.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class NormalSerializable implements Serializable
{
    private static final long serialVersionUID = 0xFFFF0001;

    private String name;
    private int age;
    private List<String> own;

    public NormalSerializable(String name, int age, String...owns)
    {
        this.name = name;
        this.age = age;
        this.own = new ArrayList<String>();
        for(String oneOfOwn : owns)
            this.own.add(oneOfOwn);
    }
}
