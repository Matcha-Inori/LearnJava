package com.matcha.collection;

import sun.jvm.hotspot.opto.Block;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matcha on 2017/8/1.
 */
public class TestLinkedBlockingQueue
{
    public static void main(String[] args)
    {
        TestLinkedBlockingQueue testLinkedBlockingQueue = new TestLinkedBlockingQueue();
        testLinkedBlockingQueue.test();
    }

    private void test()
    {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(3);
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        Thread getElementThread = new Thread(
                threadGroup,
                new GetElementRunnable(blockingQueue),
                "GetElementThread"
        );
        Thread putElementThread = new Thread(
                threadGroup,
                new PutElementRunnable(blockingQueue),
                "PetElementThread"
        );
        getElementThread.start();
        putElementThread.start();
    }

    private class GetElementRunnable implements Runnable
    {
        private BlockingQueue<String> blockingQueue;

        public GetElementRunnable(BlockingQueue<String> blockingQueue)
        {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run()
        {
            try
            {
                this.blockingQueue.element();
                this.blockingQueue.peek();

                this.blockingQueue.remove();
                this.blockingQueue.poll();
                this.blockingQueue.poll(10, TimeUnit.SECONDS);
                this.blockingQueue.take();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class PutElementRunnable implements Runnable
    {
        private BlockingQueue<String> blockingQueue;

        public PutElementRunnable(BlockingQueue<String> blockingQueue)
        {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run()
        {
            try
            {
                this.blockingQueue.add("A");
                this.blockingQueue.offer("B");
                this.blockingQueue.offer("C", 10, TimeUnit.SECONDS);
                this.blockingQueue.put("D");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
