package com.matcha.lock.app;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/2/23.
 */
public class MyLinkedBlockingQueue<ELEMENT> implements BlockingQueue<ELEMENT>
{
    private ELEMENT[] elements;
    private final int queueSize;
    private int startIndex;
    private int insertIndex;

    private Lock lock;
    private Condition empty;
    private Condition full;

    public MyLinkedBlockingQueue(Class<ELEMENT> elementClass)
    {
        this(10, elementClass);
    }

    public MyLinkedBlockingQueue(int queueSize, Class<ELEMENT> elementClass)
    {
        this.elements = (ELEMENT[]) Array.newInstance(elementClass, queueSize);
        this.queueSize = queueSize;
        this.startIndex = 0;
        this.insertIndex = 0;
        this.lock = new ReentrantLock(true);
        this.empty = lock.newCondition();
        this.full = lock.newCondition();
    }

    @Override
    public boolean add(ELEMENT element)
    {
        if (offer(element))
            return true;
        else
            throw new IllegalStateException("Queue full");
    }

    @Override
    public boolean offer(ELEMENT element)
    {
        lock.lock();
        try
        {
            if(!hasPosition())
                return false;
            addElement(element);
            return true;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void put(ELEMENT element) throws InterruptedException
    {
        lock.lock();
        try
        {
            if(!hasPosition())
                full.await();
            offer(element);
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(ELEMENT element, long timeout, TimeUnit unit) throws InterruptedException
    {
        lock.lock();
        try
        {
            boolean shouldPut = true;
            if(!hasPosition())
                shouldPut = full.await(timeout, unit);
            return shouldPut ? offer(element) : shouldPut;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public ELEMENT take() throws InterruptedException
    {
        lock.lock();
        try
        {
            if(isEmpty())
                empty.await();
            return poll();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public ELEMENT poll(long timeout, TimeUnit unit) throws InterruptedException
    {
        lock.lock();
        try
        {
            boolean shouldRead = true;
            if(isEmpty())
                shouldRead = empty.await(timeout, unit);
            return shouldRead ? poll() : null;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public int remainingCapacity()
    {
        return 0;
    }

    @Override
    public boolean remove(Object o)
    {

    }

    @Override
    public boolean contains(Object o)
    {
        return false;
    }

    @Override
    public int drainTo(Collection<? super ELEMENT> c)
    {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super ELEMENT> c, int maxElements)
    {
        return 0;
    }

    @Override
    public ELEMENT remove()
    {
        lock.lock();
        try
        {
            if(isEmpty())
                throw new NoSuchElementException();
            else
                return poll();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public ELEMENT poll()
    {
        lock.lock();
        try
        {
            if(isEmpty())
                return null;
            return getElement();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public ELEMENT element()
    {
        lock.lock();
        try
        {
            if(isEmpty())
                throw new NoSuchElementException();
            return elements[startIndex];
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public ELEMENT peek()
    {
        lock.lock();
        try
        {
            return isEmpty() ? null : elements[startIndex];
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public int size()
    {
        return insertIndex - startIndex;
    }

    @Override
    public boolean isEmpty()
    {
        return startIndex == insertIndex;
    }

    @Override
    public Iterator<ELEMENT> iterator()
    {
        return null;
    }

    @Override
    public Object[] toArray()
    {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ELEMENT> c)
    {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public void clear()
    {

    }

    private boolean hasPosition()
    {
        return insertIndex < queueSize;
    }

    private void addElement(ELEMENT element)
    {
        elements[insertIndex++] = element;
        cleanArray();
    }

    private ELEMENT getElement()
    {
        return elements[startIndex++];
    }

    private void cleanArray()
    {
        if(insertIndex < queueSize)
            return;
        int length = insertIndex - startIndex;
        System.arraycopy(elements, startIndex, elements, 0, length);
    }
}