package com.matcha.security.context.login;

import com.matcha.security.context.login.callback.MatchaCallbackHandler;
import com.matcha.security.context.login.db.UserPasswordDB;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Matcha on 2017/1/2.
 */
public class MatchaLoginModule implements LoginModule
{
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    private Principal entity;

    @Override
    public void initialize(Subject subject,
                           CallbackHandler callbackHandler,
                           Map<String, ?> sharedState,
                           Map<String, ?> options)
    {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        this.entity = null;
    }

    @Override
    public boolean login() throws LoginException
    {
        NameCallback nameCallback = new NameCallback("Please enter your user name : ");
        PasswordCallback passwordCallback =
                new PasswordCallback("Please enter your password : ", false);
        Callback[] callbacks = new Callback[]{
                nameCallback,
                passwordCallback
        };

        try
        {
            callbackHandler.handle(callbacks);
        }
        catch (IOException | UnsupportedCallbackException e)
        {
            e.printStackTrace();
            throw new LoginException(e.getMessage());
        }

        String userName = nameCallback.getName();
        char[] password = passwordCallback.getPassword();

        UserPasswordDB userPasswordDB = UserPasswordDB.getInstance();
        boolean isPass = userPasswordDB.checkPassword(userName, password);
        passwordCallback.clearPassword();
        Arrays.fill(password, ' ');
        if(!isPass)
            return false;
        this.entity = new MatchaPrincipal(userName);
        return true;
    }

    @Override
    public boolean commit() throws LoginException
    {
        this.subject.getPrincipals().add(this.entity);
        this.entity = null;
        return true;
    }

    @Override
    public boolean abort() throws LoginException
    {
        this.subject.getPrincipals().remove(this.entity);
        this.entity = null;
        return true;
    }

    @Override
    public boolean logout() throws LoginException
    {
        this.subject.getPrincipals().remove(this.entity);
        this.entity = null;
        return true;
    }
}
