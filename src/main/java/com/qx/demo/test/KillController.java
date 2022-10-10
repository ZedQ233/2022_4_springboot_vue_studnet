package com.qx.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZedQ
 * @Date: 2022/10/8 19:28
 * @Description:
 */
@RestController
@RequestMapping("/kill")
public class KillController {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/k")
    public String testRedis(@RequestParam String uid,@RequestParam String prodid){

        if(uid == null || prodid == null){
            return "用户id or 商品id 不能为空";
        }

        String kcKey = "sk:"+prodid+":qt";
        String userKey = "sk:"+uid+":user";
        Integer kc = (Integer) redisTemplate.opsForValue().get(kcKey);
        if(kc == null){
            return "秒杀还没开始亲";
        }

        if(kc<=0){
            return "秒杀已经结束了";
        }

        if(redisTemplate.opsForSet().isMember(userKey,uid)){
            return "已经秒杀成功,请更换商品进行秒杀";
        }

        redisTemplate.opsForValue().decrement(kcKey);

        redisTemplate.opsForSet().add(userKey,uid);

        return "秒杀成功!";



    }
}
