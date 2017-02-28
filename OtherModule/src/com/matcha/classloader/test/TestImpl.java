package com.matcha.classloader.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/28.
 */
public class TestImpl implements ITest
{
    private final String pattenStr;
    private final Pattern pattern;

    public TestImpl(String pattenStr)
    {
        this.pattenStr = pattenStr;
        this.pattern = Pattern.compile(pattenStr);
    }

    @Override
    public String test(int param1, String param2)
    {
        Matcher matcher = pattern.matcher(param2);
        if(!matcher.matches())
            throw new RuntimeException("Not match!");
        return String.format("%d - %s", param1, param2);
    }
}
