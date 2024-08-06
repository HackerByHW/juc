package com.atguigu.study;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: ReentrantLockDemo
 * Package: com.atguigu.study
 * Description:
 *
 * 可重入锁又名递归锁，是指在同一个线程中，外层方法获取锁的时候，再进入该线程的内层方法会自动获取锁。
 * Java中ReentrantLock和synchronized都是可重入锁，可重入锁的一个优点是`可一定程度避免死锁`。
 *
 * @Author Hacker by HW
 * @Create 2024/8/6 23:14
 * @Version 1.0
 *
 **/
public class ReentrantLockDemo
{
    public synchronized void a(){
        this.b();
        System.out.println("a");
    }

    public synchronized void b(){
        System.out.println("b");
    }






    /**
     * ReentrantLock版本演示可重入锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    public void c()
    {
        lock.lock();
        try
        {
            d();
            System.out.println("c");
        }finally {
            lock.unlock();
            lock.unlock();
            lock.unlock();
            lock.unlock();
        }
    }

    public void d()
    {
        lock.lock();
        try
        {
            System.out.println("d");
        }finally {
            lock.unlock();
        }
    }




    public static void main(String[] args) {
        new ReentrantLockDemo().c();
    }
}
