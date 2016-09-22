package com.matcha.nio.server.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/9/22.
 */
public abstract class AbstractServices implements IService
{
    private final static AtomicInteger nextId;

    static
    {
        nextId = new AtomicInteger(0);
    }

    private int nextId()
    {
        return nextId.getAndIncrement();
    }

    private int id;

    protected String serviceName;

    protected AbstractServices(String serviceName)
    {
        this.id = nextId();
        this.serviceName = serviceName;
        initService();
    }

    protected void initService()
    {

    }

    @Override
    public String toString()
    {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("AbstractServices{").append("id=").append(id).append(", serviceName='").append(serviceName)
                .append('\'').append('}');
        return strBuffer.toString();
    }
}
