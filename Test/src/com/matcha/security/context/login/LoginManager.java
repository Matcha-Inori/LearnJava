package com.matcha.security.context.login;

import com.matcha.security.context.ContextManager;
import com.matcha.security.context.login.callback.MatchaCallbackHandler;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matcha on 2017/1/3.
 */
public class LoginManager
{
    private static volatile LoginManager instance;

    public static LoginManager getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new LoginManager();
    }

    private Map<String, Object[]> loginInfos;

    private LoginManager()
    {
        loginInfos = new ConcurrentHashMap<>();
    }

    public String login()
    {
        Subject subject = null;
        LoginContext loginContext = null;
        try
        {
            subject = new Subject();
            loginContext = new LoginContext("Matcha", subject, new MatchaCallbackHandler());
            loginContext.login();
        }
        catch (LoginException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        UUID sessionUuid = UUID.randomUUID();
        String sessionId = sessionUuid.toString();
        loginInfos.put(sessionId, new Object[]{subject, loginContext});
        return sessionId;
    }

    public void logout(String sessionId)
    {
        Object[] loginInfo = loginInfos.get(sessionId);
        if(loginInfo == null)
            return;
        LoginContext loginContexts = (LoginContext) loginInfo[1];
        try
        {
            loginContexts.logout();
        }
        catch (LoginException e)
        {
            e.printStackTrace();
        }
        loginInfos.remove(sessionId);
        ContextManager.getInstance().removeContext(sessionId);
    }

    public <T> T doAs(String sessionId, PrivilegedAction<T> privilegedAction)
    {
        Object[] loginInfo = loginInfos.get(sessionId);
        if(loginInfo == null)
            return null;

        Subject subject = (Subject) loginInfo[0];
        return Subject.doAs(subject, privilegedAction);
    }
}
