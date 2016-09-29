package com.matcha.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.*;

/**
 * Created by Matcha on 16/9/29.
 */
public class TestCipher
{
    public static void main(String[] args)
    {
        try
        {
            Charset charset = Charset.forName("UTF-8");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            Cipher cipherEncrypt = Cipher.getInstance("RSA");
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encode = cipherEncrypt.doFinal("今天也是个好日子!!!".getBytes(charset));
            String encodeStr = new String(encode, charset);
            System.out.println(encodeStr);
            Cipher cipherDecrypt = Cipher.getInstance("RSA");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decode = cipherDecrypt.doFinal(encode);
            String str = new String(decode, charset);
            System.out.println(str);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
