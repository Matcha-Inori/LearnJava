package com.matcha.generic.app;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Matcha on 16/8/14.
 */
public class GenericArrayWithObject<T> implements IGenericArray<T>
{
    private Object[] members;
    private int size;

    public GenericArrayWithObject(int size)
    {
        this.members = new Object[size];
        this.size = 0;
    }
    @Override
    public void add(T member)
    {
        if(size == members.length - 1)
            grow();
        members[size++] = member;
    }

    private void grow()
    {
        int newArraySize = members.length << 1;
        members = Arrays.copyOf(members, newArraySize);
    }

    @Override
    public T get(int index)
    {
        return (T) (index >= size ? null : members[index]);
    }

    @Override
    public T remove(int index)
    {
        if(index >= size)
            return null;
        T value = (T) members[index];
        members[index] = null;
        if(index != size--)
            moveMembers(index);
        return value;
    }

    private void moveMembers(int startIndex)
    {
        System.arraycopy(members, startIndex + 1, members, startIndex, size - startIndex);
    }

    @Override
    public Iterator<T> iterator()
    {
        List<T> cloneList = Arrays.asList((T[]) Arrays.copyOf(members, size));
        return cloneList.iterator();
    }
}
