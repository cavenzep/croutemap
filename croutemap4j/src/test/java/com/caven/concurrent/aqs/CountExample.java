/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/2/20 下午10:19
 */
@Slf4j
public class CountExample {

    //请求总数
    public static int clientTotal = 5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;
    //变量声明：计数
    //public static AtomicInteger count = new AtomicInteger(0);
    public static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();//创建线程池
        final Semaphore semaphore = new Semaphore(threadTotal);//定义信号量，给出允许并发的数目
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);//定义计数器闭锁
        //log.info("count:{}", count.get());//变量取值
        log.info("count:{}", count);//变量取值
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();//判断进程是否允许被执行
                    add();
                    semaphore.release();//释放进程
                } catch (InterruptedException e) {
                    log.error("Exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();//保证信号量减为0
        executorService.shutdown();//关闭线程池
        log.info("count:{}", count);//变量取值
    }

    private static synchronized void add() {
        //count.incrementAndGet();//变量操作
        count++;
    }
}

