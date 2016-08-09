package com.matcha.list;

import com.matcha.list.app.A;
import com.matcha.list.app.B;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class TestList
{
    public static void main(String[] args)
    {
        List list = new ArrayList<String>();
        list.add("1234");
        list.add(1234);
        System.out.println("success");
        List<String> list2 = list;
//        list2.add(1234);


        List<?> otherList = new ArrayList<B>();
        List<A> otherList1 = (List<A>) otherList;
        otherList1.add(new A());

        List<B> listA = new ArrayList<B>();
        List<?> listB = listA;
        List<A> listC = (List<A>) listB;
        listC.add(new A());
        listC.add(new B());
//        listA.add(new A());
        listA.add(new B());
//        B b = listA.get(0);
        listA.get(0);
    }
}