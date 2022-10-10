package com.qx.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZedQ
 * @Date: 2022/10/8 15:08
 * @Description:
 */

@RestController
@RequestMapping("/test")
public class RedisController {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public String testRedis(){
        redisTemplate.opsForValue().set("name","qx");
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }
}
