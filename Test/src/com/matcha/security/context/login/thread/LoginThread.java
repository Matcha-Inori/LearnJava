package com.matcha.security.context.login.thread;

import com.matcha.security.context.login.LoginManager;
import com.matcha.security.context.test.package1.A;

/**
 * Created by Matcha on 2017/1/3.
 */
public class LoginThread implements Runnable
{
    private static ThreadLocal<String> sessionId = new ThreadLocal<>();

    @Override
    public void run()
    {
        String sessionId = LoginManager.getInstance().login();
        setSessionId(sessionId);
        A a = new A();
        a.methodA();
        LoginManager.getInstance().logout(sessionId);
    }

    public static String getSessionId()
    {
        return sessionId.get();
    }

    public static void setSessionId(String sessionId)
    {
        LoginThread.sessionId.set(sessionId);
    }
}
