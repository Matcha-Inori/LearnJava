package com.matcha.security.context.test.package3;

import com.matcha.security.context.perm.MatchaPermission;
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
        D d = new D();
        d.methodD();
        AccessController.checkPermission(new MatchaPermission("name1", "false"));
    }
}
