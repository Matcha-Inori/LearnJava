package com.matcha.serch;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Sercher
{
    private ClassLoader classLoader;
    private String basePackageName;
    private Class<?> superClass;

    public Sercher(ClassLoader classLoader, String basePackageName, Class<?> superClass)
    {
        if(basePackageName == null ||
                basePackageName.isEmpty())
            throw new IllegalArgumentException("basePackageName can not be null!!!");

        this.classLoader = classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader;
        this.basePackageName = basePackageName;
        this.superClass = superClass == null ? Object.class : superClass;
    }

    public List<Class<?>> serch()
    {
        try
        {
            Set<Class<?>> classes = new HashSet<>();
            String basePackagePath = basePackageName.replace('.', '/');
            Enumeration<URL> urlEnumeration = classLoader.getResources(basePackagePath);
            URL resourceURL = null;
            String resourceProtocol = null;
            while(urlEnumeration.hasMoreElements())
            {
                resourceURL = urlEnumeration.nextElement();
                resourceProtocol = resourceURL.getProtocol();
                switch(resourceProtocol)
                {
                    case "file" :
                    {

                    }
                    case "jar" :
                    {

                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
