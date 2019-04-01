package com.caven.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/3/30 下午6:57
 */
public class HashMapDemo {

    @Test
    public void putDemo() {
        Map<MyKey, String> map = new HashMap(19);
        map.size();
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            map.put(new MyKey(value), "key-".concat(value));
        }
        System.out.println(map);
    }

    @Test
    public void putCurrentDemo() {
        Map<MyKey, String> map = new ConcurrentHashMap(16);
        map.size();
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            map.put(new MyKey(value), "key-".concat(value));
        }
        System.out.println(map);
    }

    @Data
    @AllArgsConstructor
    public final class MyKey {
        private String value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MyKey)) return false;
            MyKey myKey = (MyKey) o;
            return Objects.equals(value, myKey.value);
        }

        @Override
        public int hashCode() {
            return 16;
        }
    }
}
