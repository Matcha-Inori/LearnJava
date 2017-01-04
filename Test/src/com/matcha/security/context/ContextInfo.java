package com.matcha.security.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matcha on 2017/1/3.
 */
public class ContextInfo
{
    private Map<String, Object> params;

    public ContextInfo()
    {
        params = new HashMap<String, Object>();
    }

    public Object put(String key, Object value)
    {
        return params.put(key, value);
    }

    public Object get(String key)
    {
        return params.get(key);
    }

    public Object get(String key, Object defaultValue)
    {
        Object value = params.get(key);
        return value == null ? defaultValue : value;
    }
}
