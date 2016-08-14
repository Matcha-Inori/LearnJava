package com.matcha.generic;

import com.matcha.generic.app.GenericArrayType;
import com.matcha.generic.app.IGenericArray;
import com.matcha.generic.factory.DefaultGenericArrayFacotry;
import com.matcha.generic.factory.IGenericArrayFacotry;
import com.matcha.generic.object.A;
import com.matcha.generic.object.C;

/**
 * Created by Administrator on 2016/8/9.
 */
public class TestGeneric
{
    public static void main(String[] args)
    {
        IGenericArrayFacotry<A> genericArrayFacotry = new DefaultGenericArrayFacotry<A>(GenericArrayType.GENERIC_ARRAY_WITH_FT);
        IGenericArray<A> iGenericArray = genericArrayFacotry.newGenericArray(A.class);
        iGenericArray.add(new A());
        IGenericArray<?> otherIGenericArray = (IGenericArray<?>) iGenericArray;
        IGenericArray<C> theOtherIGenericArray = (IGenericArray<C>) otherIGenericArray;
        theOtherIGenericArray.add(new C());
        for(Object member : theOtherIGenericArray)
        {
            System.out.println(member);
        }
    }
}
