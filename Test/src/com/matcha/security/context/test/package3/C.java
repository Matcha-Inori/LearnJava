package com.matcha.security.context.test.package3;

import com.matcha.security.context.test.package4.D;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by Matcha on 2017/1/3.
 */
public class C
{
    public void methodC()
    {
        AccessController.doPrivileged(new PrivilegedAction<Void>()
        {
            @Override
            public Void run()
            {
                D d = new D();
                d.methodD();
                return null;
            }
        });
    }
}
