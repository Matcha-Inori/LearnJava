package com.matcha.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2017/2/28.
 */
public class TestFile
{
    public static void main(String[] args)
    {
        Path basePath;
        try
        {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL baseURL = classLoader.getResource(".");
            URI baseURI = baseURL.toURI();
            basePath = Paths.get(baseURI);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(FileSystem fileSystem = FileSystems.getDefault())
        {
            for(Path path : fileSystem.getRootDirectories())
                System.out.println(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
