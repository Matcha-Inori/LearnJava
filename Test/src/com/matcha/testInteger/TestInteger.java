package com.matcha.testInteger;

/**
 * Created by Matcha on 16/10/10.
 */
public class TestInteger
{
    public static void main(String[] args)
    {
        int integer1 = 230;
        int integer2 = 123;
        int integer3 = -89;

        System.out.println(Integer.toBinaryString(integer1));
        System.out.println(Integer.toBinaryString(integer2));
        System.out.println(Integer.toBinaryString(integer3));
        System.out.println();

        System.out.println(Byte.toString((byte) integer1));
        System.out.println(Byte.toString((byte) integer2));
        System.out.println(Byte.toString((byte) integer3));
    }
}
