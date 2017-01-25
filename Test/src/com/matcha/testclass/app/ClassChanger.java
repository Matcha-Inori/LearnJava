package com.matcha.testclass.app;

/**
 * Created by Administrator on 2017/1/25.
 */
public class ClassChanger<SUPER_CLASS, CHILD_CLASS>
{
    private Class<SUPER_CLASS> superClass;
    private Class<CHILD_CLASS> childClass;

    public ClassChanger(Class<SUPER_CLASS> superClass, Class<CHILD_CLASS> childClass)
    {
        this.superClass = superClass;
        this.childClass = childClass;
    }

    public SUPER_CLASS change(CHILD_CLASS childObj)
    {
        return superClass.cast(childObj);
    }
}
