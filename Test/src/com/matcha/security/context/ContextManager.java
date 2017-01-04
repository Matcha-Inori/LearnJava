package com.matcha.security.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matcha on 2017/1/3.
 */
public class ContextManager
{
    private static volatile ContextManager instance;

    public static ContextManager getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new ContextManager();
    }

    private Map<String, ContextInfo> contexts;

    private ContextManager()
    {
        contexts = new ConcurrentHashMap<>();
    }

    public ContextInfo createContext(String sessionId)
    {
        ContextInfo contextInfo = new ContextInfo();
        contexts.put(sessionId, contextInfo);
        return contextInfo;
    }

    public ContextInfo getContext(String sessionId)
    {
        return contexts.get(sessionId);
    }

    public ContextInfo removeContext(String sessionId)
    {
        return contexts.remove(sessionId);
    }
}
