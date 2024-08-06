package com.atguigu.study;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ClassName: NotSafeDemo
 * Package: com.atguigu.study
 * Description:
 * 线程不安全
 * @Author Hacker by HW
 * @Create 2024/8/6 22:57
 * @Version 1.0
 *
 **/
public class NotSafeDemo
{
    public static void main(String[] args)
    {
        Map<String,String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());//new HashMap<>();

        new HashMap<>().put(1,2);

        for (int i = 1; i <=100; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,6));
                System.out.println(map);
            },String.valueOf(i)).start();
        }

    }

    private static void setNotSafe()
    {
        Set<String> set = new CopyOnWriteArraySet();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();


        for (int i = 1; i <=100; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    private static void listNotSafe()
    {
        //写时复制，读写分离
        List<String> list = new CopyOnWriteArrayList();//Collections.synchronizedList(new ArrayList<>());//new Vector<>();//new ArrayList<>();


        for (int i = 1; i <=100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
//Exception in thread "52" java.util.ConcurrentModificationException

/**
 * 自己个人积累的修炼，自己的错题集，自己的技术积累
 *
 * 1 故障现象
 * 2 导致原因
 * 3 解决方案
 * 4 优化建议
 */












/*CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
        而是先将当前容器Object[]进行Copy，复制出一个新的容器Object[] newElements，
        然后向新的容器Object[] newElements里添加元素。
        添加元素后，再将原容器的引用指向新的容器setArray(newElements)。
        这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
        所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。*/
