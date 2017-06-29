package com.matcha.bean;

import com.matcha.bean.app.PersonInfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

/**
 * Created by Administrator on 2017/4/28.
 */
public class TestJavaBean
{
    public static void main(String[] args)
    {
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo(PersonInfo.class);
            System.out.println(beanInfo);
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
