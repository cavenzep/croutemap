package com.caven.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 *
 * @author	zhuerping Email:zhuerping@co-mall.com
 * @since	2019/1/23 23:41
 */
@SpringBootApplication
@RestController
public class DemoApplication {
    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    @RequestMapping("/docker")
    public String docker() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

