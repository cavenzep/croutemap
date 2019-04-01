/*
 * Copyright (c) 2010-2019 www.co-mall.com/ Inc. All rights reserved.
 * 注意：本内容仅限于北京科码先锋互联网技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.caven.lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/2/19 下午11:32
 */
public class StringDemo {
    /** LOGGER */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringDemo.class);

    @Test
    public void testString(){
        String jvm = "jvm";
        String jvm4Split = "j" + "v" + "m";
        String jvm4String = new String("jvm");
        LOGGER.info("jvm == jvm4Split [{}], jvm == jvm4String [{}]", jvm == jvm4Split, jvm == jvm4String);
        LOGGER.info("jvm equals jvm4Split [{}], jvm equals jvm4String [{}]", jvm.equals(jvm4Split), jvm.equals(jvm4String));
    }
}
