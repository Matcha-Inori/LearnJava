package com.matcha.script;

import javax.script.*;

/**
 * Created by Matcha on 2016/11/28.
 */
public class TestJavaScript
{
    public static void main(String[] args)
    {
        try
        {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
            Bindings bindings = new SimpleBindings();
            bindings.put("name", "AAA");
            scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            scriptEngine.eval("print(name)");
        }
        catch (ScriptException e)
        {
            e.printStackTrace();
        }
    }
}
