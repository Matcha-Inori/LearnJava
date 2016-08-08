package com.matcha.atomic.data;

import com.matcha.atomic.app.People;

import java.util.Random;

/**
 * Created by Matcha on 16/8/1.
 */
public class RandomPeople
{
    private static final String[] names;
    private static final Random random;

    static
    {
        names = new String[]{
            "Matcha",
            "GreenTea",
            "BlackTea",
            "ScentedTea",
            "Riven",
            "Randone",
            "A",
            "B",
            "C",
            "D"
        };
        random = new Random();
    }

    public static People randomPeople()
    {
        int nameIndex = random.nextInt(names.length);
        int age = random.nextInt(100);
        People people = new People(names[nameIndex], age);
        return people;
    }
}
