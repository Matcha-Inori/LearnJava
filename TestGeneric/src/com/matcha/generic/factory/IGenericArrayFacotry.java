package com.matcha.generic.factory;

import com.matcha.generic.app.GenericArrayType;
import com.matcha.generic.app.IGenericArray;

/**
 * Created by Matcha on 16/8/14.
 */
public interface IGenericArrayFacotry<T>
{
    IGenericArray<T> newGenericArray(Class<T> tClass);
    void changeType(GenericArrayType genericArrayType);
}
