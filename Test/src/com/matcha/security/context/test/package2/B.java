package com.matcha.security.context.test.package2;

import com.matcha.security.context.test.package3.C;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by Matcha on 2017/1/3.
 */
public class B
{
    public void methodB()
    {
        AccessController.doPrivileged(new PrivilegedAction<Void>()
        {
            @Override
            public Void run()
            {
                C c = new C();
                c.methodC();
                return null;
            }
        });
    }
}
