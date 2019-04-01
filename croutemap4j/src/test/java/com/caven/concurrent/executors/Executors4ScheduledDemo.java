/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.concurrent.executors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/3/26 下午10:55
 */
@Slf4j
public class Executors4ScheduledDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> log.warn("schedule run"), 3, TimeUnit.SECONDS);//延迟3秒执行
        executorService.shutdown();
    }
}
