package com.matcha.threadtest;

/**
 * Created by Administrator on 2016/10/9.
 */
public class TestThreadLocal
{
    private static ThreadLocal<String> name = new InheritableThreadLocal<String>(){
        @Override
        protected String initialValue()
        {
            return "riven";
        }
    };

    private static ThreadLocal<Integer> age = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue()
        {
            return 10;
        }
    };

    public static void main(String[] args)
    {
        name.set("matcha");
        age.set(100);
        Thread childThread = createThread("child thread");
        childThread.start();
    }

    private static Thread createThread(String threadName)
    {
        return new Thread(threadName){
            @Override
            public void run()
            {
                System.out.println("name is " + name.get());
                System.out.println("age is " + age.get());
            }
        };
    }
}
