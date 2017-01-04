package com.matcha.security.context.perm;

import java.security.Permission;

/**
 * Created by Matcha on 2017/1/3.
 */
public class MatchaPermission extends Permission
{
    private String action;

    public MatchaPermission(String action)
    {
        super(MatchaPermission.class.getSimpleName());
        this.action = action;
    }

    @Override
    public boolean implies(Permission permission)
    {
        if(!MatchaPermission.class.isInstance(permission))
            return false;
        return this.action.equals(permission.getActions());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!MatchaPermission.class.isInstance(obj))
            return false;
        MatchaPermission otherMatchaPerm = (MatchaPermission) obj;
        return this.action.equals(otherMatchaPerm.action);
    }

    @Override
    public int hashCode()
    {
        return this.action.hashCode();
    }

    @Override
    public String getActions()
    {
        return action;
    }
}
