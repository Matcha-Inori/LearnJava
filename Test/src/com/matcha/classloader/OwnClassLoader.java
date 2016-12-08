package com.matcha.classloader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matcha on 2016/11/28.
 */
public class OwnClassLoader extends ClassLoader
{
    private static AtomicInteger nextId;

    static
    {
        nextId = new AtomicInteger(1);
    }

    private int id;
    private URI baseURI;

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
            baseURI = baseURL.toURI().resolve("../../out/production/OtherModule/");
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
        System.out.println("Own Class Loader loadClass - name " + name);
        Class<?> theClass = null;

        String classPath = name.replace('.', '/') + ".class";
        URI classURI = baseURI.resolve(classPath);

        File classFile = new File(classURI);
        if(!classFile.exists())
            return super.loadClass(name, resolve);

        try(
                RandomAccessFile randomAccessFile = new RandomAccessFile(new File(classURI), "r");
                FileChannel fileChannel = randomAccessFile.getChannel();
        )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) fileChannel.size());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            theClass = defineClass(name, bytes, 0, bytes.length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return theClass == null ? super.loadClass(name, resolve) : theClass;
    }
}
