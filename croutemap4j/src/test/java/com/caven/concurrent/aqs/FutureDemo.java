/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/3/26 上午8:01
 */
@Slf4j
public class FutureDemo {

    /*static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }*/

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        });//线程池提交任务
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();//获取不到一直阻塞
        log.info("result：{}", result);
    }
}
