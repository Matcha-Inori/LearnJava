package com.matcha.security.context.test.package4;

import com.matcha.security.context.login.LoginManager;
import com.matcha.security.context.login.thread.LoginThread;
import com.matcha.security.context.perm.MatchaPermission;

import javax.security.auth.Subject;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by Matcha on 2017/1/3.
 */
public class D
{
    public void methodD()
    {

        AccessController.doPrivileged(new PrivilegedAction<Void>()
        {
            @Override
            public Void run()
            {
                AccessController.checkPermission(new MatchaPermission("name2", "write"));
                return null;
            }
        });
        AccessController.checkPermission(new MatchaPermission("name3", "read"));

        LoginManager.getInstance().doAs(LoginThread.getSessionId(), new PrivilegedAction<Void>()
        {
            @Override
            public Void run()
            {
                AccessController.checkPermission(new MatchaPermission("name2", "read, write"));
                return null;
            }
        });
    }
}
