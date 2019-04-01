/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/2/20 下午11:35
 */
@Slf4j
public class ThreadLocalDemo {

    //private static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args){
        //threadLocal.set(Thread.currentThread().getId());
        ExecutorService executorService = Executors.newCachedThreadPool();//创建线程池
        final CountDownLatch countDownLatch = new CountDownLatch(10);//定义计数器闭锁

        //log.info("count:{}", count.get());//变量取值og.info("count:{}", count);//变量取值
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                ThreadLocal<Long> threadLocal = new ThreadLocal<>();
                threadLocal.set(Thread.currentThread().getId());
                Object result = threadLocal.get();
                log.info("Thread: {}, result: {}, threadLocal.get(): {}",
                        Thread.currentThread().getId(), result, threadLocal.get());
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();//保证信号量减为0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("countDownLatch.await()");
        executorService.shutdown();//关闭线程池
    }
}
