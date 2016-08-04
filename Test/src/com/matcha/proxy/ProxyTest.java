package com.matcha.proxy;

import com.matcha.proxy.app.EntityTestImp;
import com.matcha.proxy.appinterface.IEntity;
import com.matcha.proxy.handler.EntityTestHandler;

import java.lang.reflect.Proxy;

/**
 * Created by rd_xidong_ren on 2016/7/18.
 */
public class ProxyTest
{
    public static void main(String[] args)
    {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IEntity EntityProxy = (IEntity) Proxy.newProxyInstance(EntityTestImp.class.getClassLoader(),
                new Class<?>[]{IEntity.class}, new EntityTestHandler());
        EntityProxy.showText();
        EntityProxy.setText("ABCDEFG");
        EntityProxy.showText();
    }
}
