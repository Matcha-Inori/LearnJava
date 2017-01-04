package com.matcha.security;

import com.matcha.security.thread.SecurityRunnable;

/**
 * Created by Matcha on 2016/12/13.
 */
public class TestSecurity
{
    public static void main(String[] args)
    {
        new Thread(new SecurityRunnable(),"Security-start").start();
    }
}
