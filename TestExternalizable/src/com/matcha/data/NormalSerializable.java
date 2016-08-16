package com.matcha.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class NormalSerializable implements Serializable
{
    private static final long serialVersionUID = 0xFF01;

    private String name;
    private int age;
    private List<String> own;
    private transient String noSerialInfo;

    private ExternalizableObj externalizableObj;

    public NormalSerializable(String name, String noSerialInfo, int age, ExternalizableObj externalizableObj,
                              String...owns)
    {
        this.name = name;
        this.noSerialInfo = noSerialInfo;
        this.age = age;
        this.externalizableObj = externalizableObj;
        this.own = new ArrayList<String>();
        for(String oneOfOwn : owns)
            this.own.add(oneOfOwn);
    }

    @Override
    public String toString()
    {
        return "NormalSerializable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", own=" + own +
                ", noSerialInfo='" + noSerialInfo + '\'' +
                ", externalizableObj=" + externalizableObj +
                '}';
    }
}
