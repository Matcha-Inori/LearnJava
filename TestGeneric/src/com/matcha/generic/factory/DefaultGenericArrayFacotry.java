package com.matcha.generic.factory;

import com.matcha.generic.app.*;

/**
 * Created by Matcha on 16/8/14.
 */
public class DefaultGenericArrayFacotry<T> implements IGenericArrayFacotry<T>
{
    private GenericArrayType genericArrayType;

    public DefaultGenericArrayFacotry()
    {
        this.genericArrayType = GenericArrayType.GENERIC_ARRAY_WITH_T;
    }

    public DefaultGenericArrayFacotry(GenericArrayType genericArrayType)
    {
        this.genericArrayType = genericArrayType;
    }

    @Override
    public IGenericArray<T> newGenericArray(Class<T> tClass)
    {
        IGenericArray<T> iGenericArray = null;
        switch (genericArrayType)
        {
            case GENERIC_ARRAY_WITH_T:
            {
                iGenericArray = new GenericArrayWithT<T>(tClass, 10);
                break;
            }
            case GENERIC_ARRAY_WITH_OBJECT:
            {
                iGenericArray = new GenericArrayWithObject<T>(10);
                break;
            }
            case GENERIC_ARRAY_WITH_FT:
            {
                iGenericArray = new GenericArrayWithFT<T>(10);
                break;
            }
            default:
            {
                iGenericArray = null;
                break;
            }
        }
        return iGenericArray;
    }

    @Override
    public void changeType(GenericArrayType genericArrayType)
    {
        this.genericArrayType = genericArrayType;
    }
}
