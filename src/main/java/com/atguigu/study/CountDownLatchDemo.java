package com.atguigu.study;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: CountDownLatchDemo
 * Package: com.atguigu.study
 * Description:
 *
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 *
 * @Author Hacker by HW
 * @Create 2024/8/6 22:50
 * @Version 1.0
 *
 **/
public class CountDownLatchDemo
{
    public static void main(String[] args) throws InterruptedException
    {

        CountDownLatch countDownLatch = new CountDownLatch(6);


        for (int i = 1; i <=6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t"+"---离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t"+"值班同学关门走人");
    }
}
