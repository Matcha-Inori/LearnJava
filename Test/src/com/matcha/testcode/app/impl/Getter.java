package com.matcha.testcode.app.impl;

import com.matcha.testcode.app.IGetter;

/**
 * Created by Matcha on 2016/11/29.
 */
public class Getter implements IGetter<String>
{
    @Override
    public String get(String s)
    {
        StringBuffer returnStrBuffer = new StringBuffer(s);
        returnStrBuffer.append("Riven");
        return returnStrBuffer.toString();
    }
}
