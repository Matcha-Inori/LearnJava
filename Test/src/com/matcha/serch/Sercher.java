package com.matcha.serch;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Sercher
{
    private ClassLoader classLoader;
    private String basePath;
    private Class<?> superClass;

    public Sercher(ClassLoader classLoader, String basePath, Class<?> superClass)
    {
        if(classLoader == null)
            throw new IllegalArgumentException("classloader can not be null!!!");
        if(basePath == null)
            throw new IllegalArgumentException("basePath can not be null!!!");
        if(superClass == null)
            throw new IllegalArgumentException("superClass can not be null!!!");

        this.classLoader = classLoader;
        this.basePath = basePath;
        this.superClass = superClass;
    }

    public List<Class<?>> serch()
    {
        classLoader.getResource(basePath);
    }
}
