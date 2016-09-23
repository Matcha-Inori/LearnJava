package com.matcha.search;

import com.matcha.search.app.A;

import java.util.Set;

/**
 * Created by Matcha on 16/9/22.
 */
public class TestSearch
{
    public static void main(String[] args)
    {
        Searcher searcher = new Searcher(Thread.currentThread().getContextClassLoader(),
                "com.matcha.search.app", A.class);
        Set<Class<?>> classSet = searcher.search();
        for(Class<?> theClass : classSet)
        {
            System.out.println(theClass.getName());
        }
    }
}
