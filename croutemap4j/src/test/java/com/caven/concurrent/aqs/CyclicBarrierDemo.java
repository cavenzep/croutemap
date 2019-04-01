/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/3/26 上午7:43
 */
@Slf4j
public class CyclicBarrierDemo {
    //公共线程循环调用方法
    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    //使用方法1：每个线程都持续等待
    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        barrier.await();
        log.info("{} continue", threadNum);
    }

    //使用方法2：每个线程只等待一段时间
//    private static void race(int threadNum) throws Exception {
//        Thread.sleep(1000);
//        try {
//            barrier.await(2000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
//            log.warn("BarrierException", e);
//        }
//    }

    //使用方法3：在初始化的时候设置runnable，当线程达到屏障时优先执行runnable
//    private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
//        log.info("callback is running");
//    });
}
