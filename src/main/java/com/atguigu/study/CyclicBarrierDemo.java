package com.atguigu.study;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ClassName: CyclicBarrierDemo
 * Package: com.atguigu.study
 * Description:
 *
 * CyclicBarrier
 * 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。它要做的事情是，
 * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
 * 线程进入屏障通过CyclicBarrier的await()方法。
 *
 * 集齐7颗龙珠就可以召唤神龙
 * @Author Hacker by HW
 * @Create 2024/8/6 22:54
 * @Version 1.0
 *
 **/
public class CyclicBarrierDemo
{
    public static void main(String[] args)
    {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {System.out.println("----召唤神龙....");});


        for (int i = 1; i <=7; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"\t"+"拿到了第："+ finalI +"\t龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            },String.valueOf(i)).start();
        }


    }
}
