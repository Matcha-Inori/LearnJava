package com.matcha.atomic.app;

/**
 * Created by Matcha on 16/8/1.
 */
public class People
{
    private String name;
    private int age;
    public volatile String model;

    public People(String name, int age)
    {
        this.name = name;
        this.age = age;
        this.model = null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                "} - " + super.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new People(this.name, this.age);
    }

    public People clonePeople() throws CloneNotSupportedException
    {
        return (People) clone();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!People.class.isInstance(obj))
            return false;

        People otherPeople = (People) obj;
        return equalsName(otherPeople.name) && otherPeople.age == this.age;
    }

    private boolean equalsName(String otherName)
    {
        return name == null ? otherName == null : name.equals(otherName);
    }
}
