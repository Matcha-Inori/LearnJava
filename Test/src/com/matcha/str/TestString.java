package com.matcha.str;

/**
 * Created by Administrator on 2016/11/21.
 */
public class TestString
{
    public static void main(String[] args)
    {
        String str1 = "java";
        String str2 = new StringBuffer().append("ja").append("va").toString();
        String str3 = new StringBuffer().append("啦啦").append("哈哈").toString();

        System.out.println("str2 - " + (str2.intern() == str2));
        System.out.println("str3 - " + (str3.intern() == str3));
    }
}
