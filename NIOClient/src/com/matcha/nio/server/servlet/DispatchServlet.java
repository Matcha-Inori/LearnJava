package com.matcha.nio.server.servlet;

import com.matcha.nio.server.service.IService;
import com.matcha.nio.servicetype.ServiceType;

import java.nio.channels.SelectionKey;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22.
 */
public class DispatchServlet
{
    private static volatile DispatchServlet instance;

    private Map<ServiceType, IService> serviceCahce;

    private DispatchServlet()
    {
        init();
    }

    private DispatchServlet getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new DispatchServlet();
    }

    private void init()
    {

    }

    public void dispatch(SelectionKey selectionKey)
    {
        selectionKey.channel();
    }
}
