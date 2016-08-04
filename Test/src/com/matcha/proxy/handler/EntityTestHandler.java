package com.matcha.proxy.handler;

import com.matcha.proxy.app.EntityTestImp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by rd_xidong_ren on 2016/7/18.
 */
public class EntityTestHandler implements InvocationHandler
{
    private EntityTestImp entityTestImp;

    public EntityTestHandler()
    {
        entityTestImp = new EntityTestImp();
    }

    public EntityTestHandler(String text)
    {
        entityTestImp = new EntityTestImp(text);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        //这个地方proxy是动态代理生成的动态的类的对象
        return method.invoke(entityTestImp, args);
    }
}
