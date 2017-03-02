package com.matcha.testclass;

/**
 * Created by Administrator on 2017/3/2.
 */
public class TestArray
{
    public static void main(String[] args)
    {
        int[] intArray = new int[]{1, 2, 3, 4, 5};
//        Object[] intObjArray = (Object[]) intArray;

        String[] strArray = new String[]{"A", "B", "C", "D", "E"};
        Object[] strObjArray = strArray;

        Class<?> intComponentClass = intArray.getClass().getComponentType();
        System.out.println(int.class == Integer.class);
        System.out.println(int.class == intComponentClass);

        Integer[] integerArray = new Integer[]{1, 2, 3, 4, 5};
        Object[] integerObjArray = integerArray;
//        String[] integerStrArray = (String[]) integerArray;

        System.out.println(Object[].class.isAssignableFrom(int[].class));
        System.out.println(Object.class.isAssignableFrom(int.class));
        System.out.println(Object.class.isAssignableFrom(Integer.class));

//        Integer[] strIntegerArray = Arrays.copyOf(strArray, strArray.length, Integer[].class);
//        for(Integer strInteger : strIntegerArray)
//            System.out.println(strInteger);

        int intNumber = 20;
        System.out.println(Integer.class.isInstance(intNumber));
        System.out.println(int.class.isInstance(intNumber));
//        System.out.println(intNumber instanceof Integer);

//        Integer[] newIntegerArray = (Integer[]) Array.newInstance(Integer.class, intArray.length);
//        System.arraycopy(newIntegerArray, 0, intArray, 0, intArray.length);
    }
}
