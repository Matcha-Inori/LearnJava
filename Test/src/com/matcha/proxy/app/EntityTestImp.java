package com.matcha.proxy.app;

import com.matcha.proxy.appinterface.IEntity;

/**
 * Created by rd_xidong_ren on 2016/7/18.
 */
public class EntityTestImp implements IEntity
{
    private String text;

    public EntityTestImp()
    {
        text = "default";
    }

    public EntityTestImp(String text)
    {
        this.text = text;
    }

    @Override
    public void setText(String newText)
    {
        this.text = newText;
    }

    @Override
    public String showText()
    {
        System.out.println("EntityTestImp ! " + this.text);
        return this.text;
    }
}
