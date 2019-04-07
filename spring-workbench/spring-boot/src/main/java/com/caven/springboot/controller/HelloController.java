package main.java.com.caven.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuerping Email:zhuerping@co-mall.com
 * @since 2019/4/3 下午10:24
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String home() {
        return "Hello Spring Boot";
    }

    @RequestMapping("/docker")
    public String docker() {
        return "Hello Docker";
    }

}
