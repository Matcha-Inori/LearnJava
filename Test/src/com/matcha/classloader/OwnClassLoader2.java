package com.matcha.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * Created by Matcha on 2017/4/25.
 */
public class OwnClassLoader2 extends ClassLoader
{
    private MethodHandle findBootstrapClassOrNullMethodHandle;
    private MethodHandle getBootstrapResourceMethodHandle;

    public OwnClassLoader2()
    {
        try
        {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType findClassMethodType = MethodType.methodType(Class.class, String.class);
            MethodType getResourceMethodType = MethodType.methodType(URL.class, String.class);
            this.findBootstrapClassOrNullMethodHandle = lookup.findVirtual(
                    ClassLoader.class,
                    "findBootstrapClassOrNull",
                    findClassMethodType
            );
            this.getBootstrapResourceMethodHandle = lookup.findVirtual(
                    ClassLoader.class,
                    "getBootstrapResource",
                    getResourceMethodType
            );
        }
        catch (NoSuchMethodException | IllegalAccessException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
    {
        synchronized (this.getClassLoadingLock(name))
        {
            try
            {
                //Bootstrap ClassLoader中的类在这里是无法查询到的
                //另一个方法findBootstrapClassOrNull在ClassLoader类中是私有的，无法调用
                Class theClass = this.findLoadedClass(name);
                if(theClass == null)
                    theClass = (Class) this.findBootstrapClassOrNullMethodHandle.invoke(name);
                if(theClass != null)
                    return loadLoadedClass(name, resolve, theClass);
            }
            catch (ClassNotFoundException e)
            {

            }
            catch (Throwable throwable)
            {

            }

            try
            {
                tryLoadBootstrapClass(name, resolve);
            }
            catch (ClassNotFoundException e)
            {

            }

            return super.loadClass(name, resolve);
        }
    }

    private Class<?> tryLoadBootstrapClass(String name, boolean resolve) throws ClassNotFoundException
    {
        try
        {
            String path = name.replace('.', '/') + ".class";
            URL classURL = (URL) this.getBootstrapResourceMethodHandle.invoke(path);
            return loadClass(classURL, name, resolve);
        }
        catch (Throwable throwable)
        {
            throw new ClassNotFoundException();
        }
    }

    private Class<?> loadLoadedClass(String name, boolean resolve, Class theClass) throws ClassNotFoundException
    {
        ProtectionDomain protectionDomain = theClass.getProtectionDomain();
        CodeSource codeSource = protectionDomain == null ? null : protectionDomain.getCodeSource();
        URL location = codeSource == null ? null : codeSource.getLocation();
        return this.loadClass(location, name, resolve);
    }

    private Class<?> loadClass(URL location, String name, boolean resolve) throws ClassNotFoundException
    {
        try
        {
            if(location == null)
            {
                System.out.println("load loaded class fail");
                throw new ClassNotFoundException();
            }
            Path locationPath = Paths.get(location.toURI());
            byte[] loadedClassBytes = Files.readAllBytes(locationPath);
            Class<?> newClass = this.defineClass(name, loadedClassBytes, 0, loadedClassBytes.length);
            if(resolve)
                this.resolveClass(newClass);
            return newClass;
        }
        catch (URISyntaxException | IOException e)
        {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }
}
