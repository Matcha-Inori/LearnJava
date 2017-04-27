package com.matcha.classloader;

import sun.misc.Unsafe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
    private Method findBootstrapClassOrNull;
    private Method getBootstrapResource;
    private Unsafe unsafe;

    public OwnClassLoader2()
    {
        try
        {
            Class<ClassLoader> classLoaderClass = ClassLoader.class;
            this.findBootstrapClassOrNull =
                    classLoaderClass.getDeclaredMethod("findBootstrapClassOrNull", String.class);
            this.findBootstrapClassOrNull.setAccessible(true);
            this.getBootstrapResource = classLoaderClass.getDeclaredMethod("getBootstrapResource", String.class);
            this.getBootstrapResource.setAccessible(true);

            Class<Unsafe> unsafeClass = Unsafe.class;
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            this.unsafe = (Unsafe) unsafeField.get(null);
        }
        catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException e)
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
                if (theClass == null)
                    theClass = (Class) this.findBootstrapClassOrNull.invoke(this, name);
                if (theClass != null)
                    return loadLoadedClass(name, resolve, theClass);
            }
            catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e)
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
            URL classURL = (URL) this.getBootstrapResource.invoke(null, path);
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
            if (location == null)
            {
                System.out.println("load loaded class fail");
                throw new ClassNotFoundException();
            }
            InputStream inputStream = location.openStream();
            int available = inputStream.available();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(available);
            ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
            readableByteChannel.read(byteBuffer);
            byteBuffer.flip();
            byte[] loadedClassBytes = new byte[available];
            byteBuffer.get(loadedClassBytes);
            Class<?> newClass = this.defineClass(name, loadedClassBytes);
            if (resolve)
                this.resolveClass(newClass);
            return newClass;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Class<?> defineClass(String name, byte[] loadedClassBytes) throws ClassNotFoundException
    {
        try
        {
            return this.defineClass(name, loadedClassBytes, 0, loadedClassBytes.length);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        try
        {
            return unsafe.defineClass(
                    name,
                    loadedClassBytes,
                    0,
                    loadedClassBytes.length,
                    this,
                    null
            );
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        throw new ClassNotFoundException();
    }
}
