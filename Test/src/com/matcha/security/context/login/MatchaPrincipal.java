package com.matcha.security.context.login;

import java.security.Principal;

/**
 * Created by Matcha on 2017/1/2.
 */
public class MatchaPrincipal implements Principal
{
    private String userName;

    public MatchaPrincipal(String userName)
    {
        this.userName = userName;
    }

    @Override
    public String getName()
    {
        return userName;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!MatchaPrincipal.class.isInstance(obj))
            return false;
        MatchaPrincipal otherMatchaPrincipal = (MatchaPrincipal) obj;
        if(userName == null)
            return otherMatchaPrincipal.userName == null;
        return userName.equals(otherMatchaPrincipal.userName);
    }

    @Override
    public String toString()
    {
        return "MatchaPrincipal{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
