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
public class OwnClassLoader extends ClassLoader implements Closeable
{
    private static final AtomicInteger nextId;
    private static final Map<OwnClassLoader, Reference<OwnClassLoader>> weakReferenceMap;
    private static final ReferenceQueue<OwnClassLoader> weakReferenceQueue;

    static
    {
        nextId = new AtomicInteger(1);
        weakReferenceMap = new ConcurrentHashMap<>(10);
        weakReferenceQueue = new ReferenceQueue<>();
    }

    private int id;
    private FileSystem fileSystem;
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
            fileSystem = FileSystems.getDefault();
            Reference<OwnClassLoader> reference = new WeakReference<>(this, weakReferenceQueue);
            weakReferenceMap.put(this, reference);
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
        try
        {
            System.out.println("Own Class Loader loadClass - name " + name);
            String classPathStr = name.replace('.', '/') + ".class";
            Path classPath = basePath.resolve(classPathStr);
            if(Files.notExists(classPath))
                return super.loadClass(name, resolve);
            byte[] classBytes = Files.readAllBytes(classPath);
            Class<?> theClass = this.defineClass(name, classBytes, 0, classBytes.length);
            return theClass == null ? super.loadClass(name, resolve) : theClass;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException
    {
        System.out.println("OwnClassLoader close");
        try
        {
            if(fileSystem != null)
                fileSystem.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static
    {
        Thread cleanerThread = new Thread(new Cleaner(), "CleanerThread");
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    private static class Cleaner implements Runnable
    {
        @Override
        public void run()
        {
            Reference<? extends OwnClassLoader> reference;
            OwnClassLoader ownClassLoader;
            while(true)
            {
                try
                {
                    reference = weakReferenceQueue.remove();
                    ownClassLoader = reference.get();
                    weakReferenceMap.remove(ownClassLoader);
                    ownClassLoader.close();
                }
                catch (InterruptedException | IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
