package com.atguigu.study;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 老版 synchronized + wait + notifyAll
 */
/*
class AirCondition
{
    int number = 0; //有一个初始值为零的变量

    public synchronized void increment() throws InterruptedException
    {
        //1 判断
        while(number != 0)
        {
            // A  C
            this.wait();
        }
        //2 干活
        System.out.println(Thread.currentThread().getName()+"\t"+(++number));
        //3 通知
        this.notifyAll();
    }

    public synchronized void decrement()throws InterruptedException
    {
        //1 判断
        while(number == 0)
        {
            this.wait();
        }
        //2 干活
        System.out.println(Thread.currentThread().getName()+"\t"+(--number));
        //3 通知
        this.notifyAll();
    }

}
*/


/**
 * 新版JUC写法  Lock + condition.await() + condition.signalAll()
  */
class AirCondition
{
    int number = 0; //有一个初始值为零的变量

    final Lock lock = new ReentrantLock();
    final Condition condition  = lock.newCondition();

    public void increment() throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(number != 0)
            {
                // A  C
                condition.await();//this.wait();
            }
            //2 干活
            System.out.println(Thread.currentThread().getName()+"\t"+(++number));
            //3 通知
            condition.signalAll();//this.notifyAll();
        }finally {
            lock.unlock();
        }
    }

    public void decrement()throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(number == 0)
            {
                condition.await();
            }
            //2 干活
            System.out.println(Thread.currentThread().getName()+"\t"+(--number));
            //3 通知
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

}

/**
 * ClassName: ProdConsumerDemo
 * Package: com.atguigu.study
 * Description:
 *
 * 两个线程，一个线程打印1-52，另一个打印字母A-Z打印顺序为12A34B...5152Z,
 * 要求用线程间通信
 *
 *
 * 有一个初始值为零的变量，现有两个线程对该变量操作，
 * 实现一个线程对变量加1，实现一个线程对变量减1,交替来10轮
 *
 *
 *  笔记：Java里面如何进行工程级别的多线程编写
 *  * 1 多线程变成模板（套路）-----上
 *  *     1.1  高内聚低耦合前提下，线程    操作    资源类
 *
 *    2  判断/干活/通知
 *
 *    3  一定要防止----虚假唤醒 //在多线程通信编码中，一定要防止----虚假唤醒，也不能出现虚假唤醒情况
 * @Author Hacker by HW
 * @Create 2024/8/6 23:07
 * @Version 1.0
 *
 **/

public class ProdConsumerDemo
{
    public static void main(String[] args)
    {
        AirCondition airCondition = new AirCondition();

        //两个线程对该变量操作，

        new Thread(() -> {
            for (int i = 1; i <=10 ; i++) {
                try {
                    airCondition.increment();
                    //暂停毫秒
                    try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <=10 ; i++) {
                try {
                    airCondition.decrement();
                    try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <=10 ; i++) {
                try {
                    airCondition.increment();
                    try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 1; i <=10 ; i++) {
                try {
                    airCondition.decrement();
                    try { TimeUnit.MILLISECONDS.sleep(400); } catch (InterruptedException e) { e.printStackTrace(); }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"D").start();


    }
}
