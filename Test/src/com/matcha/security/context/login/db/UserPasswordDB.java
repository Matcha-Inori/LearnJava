package com.matcha.security.context.login.db;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matcha on 2017/1/2.
 */
public class UserPasswordDB
{
    private static final String keyFileName = "KeyFile.dat";

    private static volatile UserPasswordDB instance;

    public static UserPasswordDB getInstance()
    {
        if(instance == null)
            createInstance();
        return instance;
    }

    private static synchronized void createInstance()
    {
        if(instance == null)
            instance = new UserPasswordDB();
    }

    private Map<String, byte[]> db;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher enCipher;
    private Cipher deCipher;

    private UserPasswordDB()
    {
        initKey();
        initCipher();
        db = new HashMap<>();
        initData();
    }

    private byte[] encodePassword(char[] password)
    {
        try
        {
            ByteBuffer input = ByteBuffer.allocate(password.length * 2);
            for(char oneChar : password)
                input.putChar(oneChar);
            input.flip();
            byte[] passwordBytes = new byte[input.remaining()];
            input.get(passwordBytes);
            return enCipher.doFinal(passwordBytes);
        }
        catch (BadPaddingException | IllegalBlockSizeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean checkPassword(String userName, char[] password)
    {
        byte[] shouldPassword = db.get(userName);
        if(shouldPassword == null)
            return false;
        byte[] passwordBytes = encodePassword(password);
        return Arrays.equals(shouldPassword, passwordBytes);
    }

    private void initData()
    {
        db.put("user", encodePassword("kduser".toCharArray()));
        db.put("administrator", encodePassword("kdadmin".toCharArray()));
        db.put("test", encodePassword("123".toCharArray()));
    }

    private void initCipher()
    {
        try
        {
            enCipher = Cipher.getInstance("RSA", "BC");
            enCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            deCipher = Cipher.getInstance("RSA", "BC");
            deCipher.init(Cipher.DECRYPT_MODE, privateKey);
        }
        catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                NoSuchPaddingException |
                InvalidKeyException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void initKey()
    {
        ClassLoader classLoader = UserPasswordDB.class.getClassLoader();
        URL keyFileURL = classLoader.getResource("resource/" + keyFileName);
        if(keyFileURL == null)
        {
            initKeyAndKeyFile();
            return;
        }
        try(
                InputStream inputStream = keyFileURL.openStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        )
        {
            publicKey = (PublicKey) objectInputStream.readObject();
            privateKey = (PrivateKey) objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void initKeyAndKeyFile()
    {
        File keyFile;
        try
        {
            File file = new File("/resource");
            if(!file.exists())
                file.mkdirs();
            file = new File("resource/" + keyFileName);
            if(file.exists())
                file.delete();
            file.createNewFile();
            keyFile = file;
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(128);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        }
        catch (IOException | NoSuchAlgorithmException | NoSuchProviderException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(
                FileOutputStream fileOutputStream = new FileOutputStream(keyFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        )
        {
            objectOutputStream.writeObject(publicKey);
            objectOutputStream.writeObject(privateKey);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
