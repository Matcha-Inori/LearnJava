package com.matcha.security.context;

import com.matcha.security.context.login.thread.LoginThread;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Security;

/**
 * Created by Matcha on 2017/1/2.
 */
public class TestAccessContext
{
    public static void main(String[] args)
    {
        try
        {
            Security.addProvider(new BouncyCastleProvider());
            URL loginConfigURL = Thread.currentThread().getContextClassLoader()
                    .getResource("com/matcha/security/context/TestAccessContext.conf");
            File loginConfig = new File(loginConfigURL.toURI());
            System.setProperty("java.security.auth.login.config", loginConfig.getAbsolutePath());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Thread loginThread = new Thread(new LoginThread(),"loginThread");
        loginThread.start();
    }
}
