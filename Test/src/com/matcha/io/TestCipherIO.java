package com.matcha.io;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;

/**
 * Created by Matcha on 16/9/26.
 */
public class TestCipherIO
{
    public static void main(String[] args)
    {
        Cipher rsaCipherEncrypt = null;
        Cipher rsaCipherDecrypt = null;
        Cipher aesCipherEncrypt = null;
        SecretKey secretKey = null;
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keypair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keypair.getPublic();
            PrivateKey privateKey = keypair.getPrivate();

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            secretKey = keyGenerator.generateKey();

            rsaCipherEncrypt = Cipher.getInstance("RSA");
            rsaCipherEncrypt.init(Cipher.ENCRYPT_MODE, privateKey);

            aesCipherEncrypt = Cipher.getInstance("AES");
            aesCipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey);

            rsaCipherDecrypt = Cipher.getInstance("RSA");
            rsaCipherDecrypt.init(Cipher.DECRYPT_MODE, publicKey);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        byte[] bytesPart1 = null;
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream, rsaCipherEncrypt);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(cipherOutputStream)
        )
        {
            objectOutputStream.writeObject(secretKey);
            bytesPart1 = byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        byte[] bytesPart2 = null;
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream, aesCipherEncrypt)
        )
        {
            cipherOutputStream.write("今天也是个好天气!!!".getBytes(Charset.forName("UTF-8")));
            bytesPart2 = byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        SecretKey secretKeyFromRead = null;
        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytesPart1);
                CipherInputStream cipherInputStream = new CipherInputStream(byteArrayInputStream, rsaCipherDecrypt);
                ObjectInputStream objectInputStream = new ObjectInputStream(cipherInputStream)
        )
        {
            secretKeyFromRead = (SecretKey) objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Cipher aesCipherDecrypt = null;
        try
        {
            aesCipherDecrypt = Cipher.getInstance("AES");
            aesCipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeyFromRead);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytesPart2);
                CipherInputStream cipherInputStream = new CipherInputStream(byteArrayInputStream, aesCipherDecrypt)
        )
        {
            int available = cipherInputStream.available();
            byte[] strBytes = new byte[available];
            cipherInputStream.read(strBytes);
            String str = new String(strBytes, Charset.forName("UTF-8"));
            System.out.println(str);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
