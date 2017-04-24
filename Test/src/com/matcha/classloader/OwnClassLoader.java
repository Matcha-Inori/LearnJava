package com.matcha.classloader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Matcha on 2016/11/28.
 */
public class OwnClassLoader extends ClassLoader
{
    private static final AtomicInteger nextId;

    static
    {
        nextId = new AtomicInteger(1);
    }

    private int id;
    private Path basePath;

    public OwnClassLoader(ClassLoader parent)
    {
        super(parent);
        init();
    }

    public OwnClassLoader()
    {
        init();
    }

    private void init()
    {
        try
        {
            id = nextId.getAndIncrement();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL baseURL = classLoader.getResource(".");
            URI baseURI = baseURL.toURI().resolve("../../out/production/OtherModule/");
            basePath = Paths.get(baseURI);
        }
        catch (URISyntaxException e)
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
                System.out.println("Own Class Loader loadClass - name " + name);
                String classPathStr = name.replace('.', '/') + ".class";
                Path classPath = basePath.resolve(classPathStr);
                if(Files.notExists(classPath))
                    return super.loadClass(name, resolve);
                byte[] classBytes = Files.readAllBytes(classPath);
                Class<?> theClass = this.defineClass(name, classBytes, 0, classBytes.length);
                if(theClass == null)
                    return super.loadClass(name, resolve);
                if(resolve)
                    this.resolveClass(theClass);
                return theClass;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return super.loadClass(name, resolve);
        }
    }
}
