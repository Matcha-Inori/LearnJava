package com.matcha.generic.app;

/**
 * Created by Matcha on 16/8/14.
 */
public enum GenericArrayType
{
    GENERIC_ARRAY_WITH_T(GenericArrayWithT.class),
    GENERIC_ARRAY_WITH_OBJECT(GenericArrayWithObject.class),
    GENERIC_ARRAY_WITH_FT(GenericArrayWithFT.class);

    private Class<? extends IGenericArray> genericArrayClass;

    private GenericArrayType(Class<? extends IGenericArray> genericArrayClass)
    {
        this.genericArrayClass = genericArrayClass;
    }

    public Class<? extends IGenericArray> getGenericArrayClass()
    {
        return genericArrayClass;
    }
}
