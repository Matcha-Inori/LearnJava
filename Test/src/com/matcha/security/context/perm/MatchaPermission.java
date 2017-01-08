package com.matcha.security.context.perm;

import javax.swing.*;
import java.security.Permission;

/**
 * Created by Matcha on 2017/1/3.
 */
public class MatchaPermission extends Permission
{
    private enum ActionEnum
    {
        READ("read", 0x0001),
        WRITE("write", 0x0002);

        private String key;
        private int position;

        ActionEnum(String key, int position)
        {
            this.key = key;
            this.position = position;
        }

        public String getKey()
        {
            return key;
        }

        public int getPosition()
        {
            return position;
        }
    }

    private String action;

    public MatchaPermission(String name, String action)
    {
        super(name);
        this.action = action;
    }

    @Override
    public boolean implies(Permission permission)
    {
        if(!MatchaPermission.class.isInstance(permission))
            return false;
        int permissionPosition = getPosition(permission.getActions());
        int position = this.getPosition(this.action.toUpperCase());
        return (position & permissionPosition) == permissionPosition;
    }

    private int getPosition(String actionStr)
    {
        String[] actions = actionStr.split(",");
        int position = 0;
        ActionEnum actionEnum;
        for(String action : actions)
        {
            action = action.trim();
            if(action.length() == 0)
                continue;
            actionEnum = ActionEnum.valueOf(action.toUpperCase());
            position |= actionEnum.getPosition();
        }
        return position;
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
