package com.matcha.testinterface;

import com.matcha.testinterface.app.Imp;
import com.matcha.testinterface.app.InterfaceA;
import com.matcha.testinterface.app.InterfaceB;
import com.matcha.testinterface.app.InterfaceC;

import java.util.Objects;

/**
 * Created by Administrator on 2016/9/9.
 */
public class TestInterface
{
    public static void main(String[] args)
    {
        Imp imp = new Imp();
        //1、如果InterfaceC的method存在，那么这个会直接调用InterfaceC的方法
        //2、如果不存在，那么这边编译会报错
        //3、可以通过给引用强制转型来调用对应的方法
        //4、如果InterfaceC的method存在，那么强制转型也可以调用对应的方法
//        method(imp);
        method((InterfaceA) imp);
        method((InterfaceB) imp);
        method(imp);
    }

    private static void method(InterfaceA interfaceA)
    {
        Objects.requireNonNull(interfaceA);
        interfaceA.methodA("default message");
    }

    private static void method(InterfaceB interfaceB)
    {
        Objects.requireNonNull(interfaceB);
        System.out.println(interfaceB.methodB());
    }

    private static void method(InterfaceC interfaceC)
    {
        Objects.requireNonNull(interfaceC);
        interfaceC.methodA("default message");
        System.out.println(interfaceC.methodB());
    }
}
