package com.atguigu.study;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData
{
    int flag = 1;// 1:A 2:B 3:C

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public void print5()
    {
        lock.lock();
        try
        {
            //1 判断
            while(flag != 1)
            {
                condition1.await();
            }
            //2 干活
            for (int i = 1; i <=5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知
            flag = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void print10()
    {
        lock.lock();
        try
        {
            //1 判断
            while(flag != 2)
            {
                condition2.await();
            }
            //2 干活
            for (int i = 1; i <=10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知
            flag = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void print15()
    {
        lock.lock();
        try
        {
            //1 判断
            while(flag != 3)
            {
                condition3.await();
            }
            //2 干活
            for (int i = 1; i <=15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知
            flag = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}



/**
 * @auther zzyy
 * @create 2024-08-06 8:34
 * 线程间定制化调用通信
 *
 *  * 多线程之间按顺序调用，实现A->B->C
 *  * 三个线程启动，要求如下：
 *  *
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * 接着
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * ......来10轮
 */

public class ThreadOrderAccess
{
    public static void main(String[] args)
    {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print5();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print10();
            }
        },"B").start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print15();
            }
        },"C").start();
    }

}
