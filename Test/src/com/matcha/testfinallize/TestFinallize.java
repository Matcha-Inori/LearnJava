package com.matcha.testfinallize;

import com.sun.javafx.util.WeakReferenceQueue;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/23.
 */
public class TestFinallize implements IPutable
{
    private List<OtherObject> otherObjectList;

    public TestFinallize()
    {
        otherObjectList = new ArrayList<>();
    }

    public static void main(String[] args)
    {
        TestFinallize testFinallize = new TestFinallize();
        testFinallize.startTest();
    }

    public void startTest()
    {
        try
        {
            Scanner scanner = new Scanner(System.in);

            OtherObject otherObjectA = new OtherObject("A");
            OtherObject otherObjectB = new OtherObject("B");

            System.out.print("please click any key ");
            scanner.nextLine();

            ReferenceQueue<OtherObject> otherObjectReferenceQueue = new ReferenceQueue<>();
            WeakReference<OtherObject> otherObjectAWeakReference =
                    new WeakReference<OtherObject>(otherObjectA, otherObjectReferenceQueue);
            WeakReference<OtherObject> otherObjectBWeakReference =
                    new WeakReference<OtherObject>(otherObjectB, otherObjectReferenceQueue);

            Thread cleanThread = new Thread(new CleanThread(otherObjectReferenceQueue));
            cleanThread.start();

            otherObjectA = null;
            otherObjectB = null;
            System.gc();
            TimeUnit.SECONDS.sleep(5);

            System.out.print("please click any key ");
            scanner.nextLine();

            OtherObject otherObjectC = new OtherObject2("C", this);
            OtherObject otherObjectD = new OtherObject2("D", this);
            WeakReference<OtherObject> otherObjectCWeakReference =
                    new WeakReference<OtherObject>(otherObjectC, otherObjectReferenceQueue);
            WeakReference<OtherObject> otherObjectDWeakReference =
                    new WeakReference<OtherObject>(otherObjectD, otherObjectReferenceQueue);

            otherObjectC = null;
            otherObjectD = null;
            System.gc();
            TimeUnit.SECONDS.sleep(5);

            System.out.print("please click any key ");
            scanner.nextLine();

            this.clear();
            System.gc();
            TimeUnit.SECONDS.sleep(5);

            cleanThread.interrupt();
            System.out.print("please click any key ");
            scanner.nextLine();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void put(OtherObject otherObject)
    {
        otherObjectList.add(otherObject);
    }

    @Override
    public void clear()
    {
        otherObjectList.clear();
    }
}
