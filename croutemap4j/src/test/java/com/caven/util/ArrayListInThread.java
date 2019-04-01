/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/2/21 上午12:00
 */
@Slf4j
public class ArrayListInThread implements Runnable {
    //List<String> list1 = new ArrayList<String>();// not thread safe

    List<String> list1 = Collections.synchronizedList(new ArrayList<String>());// thread safe
    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 2));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        list1.add(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("mygroup");
        ArrayListInThread t = new ArrayListInThread();
        for (int i = 0; i < 1000; i++) {
            Thread th = new Thread(group, t, String.valueOf(i));
            th.start();
        }

        while (group.activeCount() > 0) {
            Thread.sleep(10);
        }
        log.info("{}", t.list1.size()); // it should be 10000 if thread safe collection is used.
    }
}
