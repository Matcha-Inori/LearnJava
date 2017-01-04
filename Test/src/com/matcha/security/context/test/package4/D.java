package com.matcha.security.context.test.package4;

import com.matcha.security.context.perm.MatchaPermission;

import java.security.AccessController;

/**
 * Created by Matcha on 2017/1/3.
 */
public class D
{
    public void methodD()
    {
        AccessController.checkPermission(new MatchaPermission("read"));
    }
}
