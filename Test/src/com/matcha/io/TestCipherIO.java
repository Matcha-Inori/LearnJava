package com.matcha.io;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

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
        Security.addProvider(new BouncyCastleProvider());

        Cipher aesCipherEncrypt = null;
        Cipher aesCipherDecrypt = null;
        SecretKey secretKey = null;
        Charset charset = null;
        try
        {
            Provider bcProvider = Security.getProvider("BC");
            charset = Charset.forName("UTF-8");

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", bcProvider);
            keyGenerator.init(128);
            secretKey = keyGenerator.generateKey();

            aesCipherEncrypt = Cipher.getInstance("AES", bcProvider);
            aesCipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey);

            aesCipherDecrypt = Cipher.getInstance("AES", bcProvider);
            aesCipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        byte[] baseStr = null;
        byte[] base64Encode = null;
        byte[] encode = null;
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream, aesCipherEncrypt)
        )
        {
            baseStr = "今天也是个好天气!!!".getBytes(charset);
            base64Encode = Base64.encode(baseStr);
            cipherOutputStream.write(base64Encode);
            encode = byteArrayOutputStream.toByteArray();
            System.out.println(new String(encode, charset));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        byte[] decode = null;
        byte[] base64Decode = null;
        try(
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encode);
                CipherInputStream cipherInputStream = new CipherInputStream(byteArrayInputStream, aesCipherDecrypt)
        )
        {
            decode = new byte[byteArrayInputStream.available()];
            cipherInputStream.read(decode);
            base64Decode = Base64.decode(decode);
            System.out.println(new String(base64Decode, charset));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
