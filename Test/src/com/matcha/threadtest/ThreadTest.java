package com.matcha.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rd_xidong_ren on 2016/7/12.
 */
public class ThreadTest {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) {
        try
        {
            List<Job> jobs = new ArrayList();
            int priority;
            Job job = null;
            Thread thread = null;
            for(int i = 0;i < 10;i++)
            {
                priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
                job = new Job(priority);
                jobs.add(job);
                thread = new Thread(job, "Matcha Thread:" + i);
                thread.setPriority(priority);
                thread.start();
            }
            notStart = false;
            TimeUnit.SECONDS.sleep(10);
            notEnd = false;
            for(Job oneJob : jobs)
            {
                System.out.println("Job Priority : " + job.priority + ", Count : " + job.jobCount);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static class Job implements Runnable
    {
        private int priority;
        private long jobCount;

        public Job(int priority)
        {
            this.priority = priority;
            this.jobCount = 0;
        }

        @Override
        public void run() {
            while(notStart)
            {
                Thread.yield();
            }
            while(notEnd)
            {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
