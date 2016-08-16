package com.matcha.data;

import sun.security.provider.Sun;

import java.io.ObjectStreamField;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Matcha on 16/8/17.
 */
public class SubNormalSerializable extends NormalSerializable
{
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID;

    static
    {
        try
        {
            serialVersionUID = 0xFF01;
            Field dequeField = SubNormalSerializable.class.getDeclaredField("deque");
            Field popCopyField = SubNormalSerializable.class.getDeclaredField("popCopy");
            Field otherNormalSerializableField = SubNormalSerializable.class.getDeclaredField("otherNormalSerializable");
            serialPersistentFields = new ObjectStreamField[]{
                    new ObjectStreamField(dequeField.getName(), dequeField.getType(), false),
                    new ObjectStreamField(popCopyField.getName(), popCopyField.getType(), false),
                    new ObjectStreamField(otherNormalSerializableField.getName(),
                            otherNormalSerializableField.getType(), false)
            };
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int startIndex;
    private int endIndex;
    private Deque<String> deque;
    private String[] popCopy;
    private OtherNormalSerializable otherNormalSerializable;

    public SubNormalSerializable(String name,
                                 String noSerialInfo,
                                 int age,
                                 ExternalizableObj externalizableObj,
                                 OtherNormalSerializable otherNormalSerializable,
                                 String... owns)
    {
        super(name, noSerialInfo, age, externalizableObj, owns);
        this.startIndex = 0;
        this.endIndex = 0;
        this.deque = new LinkedBlockingDeque<String>();
        this.popCopy = new String[0];
        this.otherNormalSerializable = otherNormalSerializable;
    }

    public void add(String ele)
    {
        if(this.deque.offerLast(ele));
            endIndex++;
    }

    public String pop()
    {
        String popEle = this.deque.pollFirst();
        if(popEle == null)
            return null;
        popCopy = Arrays.copyOf(popCopy, popCopy.length + 1);
        popCopy[popCopy.length - 1] = popEle;
        startIndex++;
        return popEle;
    }

    @Override
    public String toString()
    {
        return "SubNormalSerializable{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", deque=" + deque +
                ", popCopy=" + Arrays.toString(popCopy) +
                "} --- " + super.toString();
    }
}
