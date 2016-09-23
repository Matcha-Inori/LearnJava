package com.matcha.search;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Matcha on 16/9/22.
 */
public class Searcher
{
    private ClassLoader classLoader;
    private String basePackageName;
    private Class<?> superClass;

    public Searcher(ClassLoader classLoader, String basePackageName, Class<?> superClass)
    {
        if(basePackageName == null ||
                basePackageName.isEmpty())
            throw new IllegalArgumentException("basePackageName can not be null!!!");

        this.classLoader = classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader;
        this.basePackageName = basePackageName;
        this.superClass = superClass == null ? Object.class : superClass;
    }

    public Set<Class<?>> search()
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
                        String dirPath = URLDecoder.decode(resourceURL.getFile(), "UTF-8");
                        findAndAddClassesInPackageByFile(basePackageName, dirPath, classes);
                        break;
                    }
                    case "jar" :
                    {
                        JarFile jarFile = ((JarURLConnection) resourceURL.openConnection()).getJarFile();
                        Enumeration<JarEntry> enumeration = jarFile.entries();
                        JarEntry jarEntry = null;
                        while(enumeration.hasMoreElements())
                        {
                            jarEntry = enumeration.nextElement();
                        }
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    private void findAndAddClassesInPackageByFile(String packageName,
                                                  String dirPath,
                                                  Set<Class<?>> classes) throws ClassNotFoundException
    {
        File dirFile = new File(dirPath);
        if(!dirFile.exists() || !dirFile.isDirectory())
            return;

        File[] childFiles = dirFile.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                if(pathname.isDirectory())
                    return true;
                String pathName = pathname.getName();
                return pathName.endsWith(".class");
            }
        });

        String childFileName = null;
        String className = null;
        for(File childFile : childFiles)
        {
            if(childFile.isDirectory())
                findAndAddClassesInPackageByFile(childFile.getName(), childFile.getAbsolutePath(), classes);
            else
            {
                childFileName = childFile.getName();
                className = childFileName.substring(0, childFileName.length() - 6);
                Class.forName(packageName + "." + className, false, classLoader);
            }
        }
    }
}
