package com.matcha.testequals;

/**
 * Created by Administrator on 2017/2/25.
 */
public class Test
{
    public static void main(String[] args)
    {
        Node node1 = new Node();
        Node node2 = new Node();

        Node tail = node1;
        Node t = node2;

        System.out.println(t != (t = tail));
        System.out.println(t != tail);
        System.out.println("node1 - " + node1);
        System.out.println("node2 - " + node2);
        System.out.println("tail - " + tail);
        System.out.println("t - " + t);
    }

    private static class Node
    {

    }
}
