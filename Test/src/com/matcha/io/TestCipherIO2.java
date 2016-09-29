package com.matcha.io;

import javax.crypto.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;

/**
 * Created by Matcha on 16/9/29.
 */
public class TestCipherIO2
{
    public static void main(String[] args)
    {
        Cipher cipherEncrypt = null;
        Cipher cipherDecrypt = null;
        Charset charset = null;
        try
        {
            charset = Charset.forName("UTF-8");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            cipherEncrypt = Cipher.getInstance("RSA");
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, privateKey);
            cipherDecrypt = Cipher.getInstance("RSA");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, publicKey);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        byte[] encode = null;
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream, cipherEncrypt)
        )
        {
            cipherOutputStream.write("今天也是个好天气!!!".getBytes(charset));
            encode = byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        byte[] decode = null;
        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encode);
                CipherInputStream cipherInputStream = new CipherInputStream(byteArrayInputStream, cipherDecrypt)
        )
        {
            decode = new byte[cipherInputStream.available()];
            cipherInputStream.read(decode);
            String str = new String(decode, charset);
            System.out.println(str);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
