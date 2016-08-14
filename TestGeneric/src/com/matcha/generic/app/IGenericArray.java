package com.matcha.generic.app;

/**
 * Created by Matcha on 16/8/14.
 */
public interface IGenericArray<T> extends Iterable<T>
{
    void add(T member);
    T get(int index);
    T remove(int index);
}
