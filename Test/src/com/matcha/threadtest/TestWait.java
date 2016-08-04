package com.matcha.threadtest;

/**
 * Created by rd_xidong_ren on 2016/7/15.
 */
public class TestWait {
    public static void main(String[] args) {
        try
        {
            TestWait obj = new TestWait();
            obj.testWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testWait() throws InterruptedException {
        wait();
    }
}
