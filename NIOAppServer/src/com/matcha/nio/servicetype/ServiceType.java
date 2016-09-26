package com.matcha.nio.servicetype;

import com.matcha.nio.server.service.IService;
import com.matcha.nio.server.service.LoginService;

/**
 * Created by Administrator on 2016/9/22.
 */
public enum ServiceType
{
    LOGIN_SERVICE(LoginService.class, 1, true);

    private Class<? extends IService> serviceClass;
    private int serviceId;
    private boolean needSync;

    ServiceType(Class<? extends IService> serviceClass,
                int serviceId,
                boolean needSync)
    {
        this.serviceClass = serviceClass;
        this.serviceId = serviceId;
        this.needSync = needSync;
    }

    public Class<? extends IService> getServiceClass()
    {
        return serviceClass;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public boolean isNeedSync()
    {
        return needSync;
    }
}
