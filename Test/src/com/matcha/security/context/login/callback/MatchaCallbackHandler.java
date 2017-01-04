package com.matcha.security.context.login.callback;

import javax.security.auth.callback.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Matcha on 2017/1/2.
 */
public class MatchaCallbackHandler implements CallbackHandler
{
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
        for(Callback callback : callbacks)
        {
            if(NameCallback.class.isInstance(callback))
                handle((NameCallback) callback);
            else if(PasswordCallback.class.isInstance(callback))
                handle((PasswordCallback) callback);
        }
    }

    private void handle(NameCallback nameCallback)
    {
        try
        {
            System.out.print(nameCallback.getPrompt());
            StringBuffer userNameBuffer = new StringBuffer();
            for(char input = (char) System.in.read();input != '\n';input = (char) System.in.read())
                userNameBuffer.append(input);
            nameCallback.setName(userNameBuffer.toString().trim());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void handle(PasswordCallback passwordCallback)
    {
        try
        {
            System.out.print(passwordCallback.getPrompt());
            StringBuffer passWordBuffer = new StringBuffer();
            for(char input = (char) System.in.read();input != '\n';input = (char) System.in.read())
                passWordBuffer.append(input);
            passwordCallback.setPassword(passWordBuffer.toString().toCharArray());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
