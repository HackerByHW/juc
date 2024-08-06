package com.atguigu.study;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * ClassName: CASDemo
 * Package: com.atguigu.study
 * Description:
 *  100个线程，每个线程加100次，求最后的number值
 * @Author Hacker by HW
 * @Create 2024/8/6 22:44
 * @Version 1.0
 *
 **/
public class CASDemo
{

    public static final int COUNT_NUMBER = 100;

    //1 需求:多线程环境【不使用】原子类AtomicInteger保证线程安全（基本数据类型）
    private int number;
    public int getNumber()
    {
        return number;
    }
    public synchronized void setNumber()
    {
        ++number;
    }

    //2 多线程环境    【使用】   原子类AtomicInteger保证线程安全（基本数据类型）
    AtomicInteger atomicInteger = new AtomicInteger();

    public int getAtomicInteger()
    {
        return atomicInteger.get();
    }

    public void setAtomicInteger()
    {
        atomicInteger.incrementAndGet();

        atomicInteger.getAndIncrement();
    }




    public static void main(String[] args) throws InterruptedException
    {
        CASDemo casDemo = new CASDemo();
        CountDownLatch countDownLatch = new CountDownLatch(COUNT_NUMBER);

        for (int i = 1; i <=COUNT_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 1; j <=COUNT_NUMBER ; j++) {
                    casDemo.setNumber();
                    casDemo.setAtomicInteger();
                }
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        //暂停几秒钟线程
        //try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t"+casDemo.getNumber());
        System.out.println(Thread.currentThread().getName()+"\t"+casDemo.getAtomicInteger());



    }




    private static void helloCAS()
    {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 422)+"\t"+atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024)+"\t"+atomicInteger.get());
    }
}
