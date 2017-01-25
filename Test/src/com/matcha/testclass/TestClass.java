package com.matcha.testclass;

import com.matcha.testclass.app.A;
import com.matcha.testclass.app.B;
import com.matcha.testclass.app.C;
import com.matcha.testclass.app.ClassChanger;

/**
 * Created by Administrator on 2017/1/25.
 */
public class TestClass
{
    public static void main(String[] args)
    {
        ClassChanger<B, C> classChanger = new ClassChanger<>(B.class, C.class);
        C c = new C();
        B b = classChanger.change(c);
        Class<? extends A> bClass = B.class.asSubclass(A.class);
    }
}
