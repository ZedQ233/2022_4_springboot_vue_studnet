package com.qx.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.qx.demo.common.Result;
import com.qx.demo.entity.User;
import com.qx.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: ZedQ
 * @Date: 2022/10/10 19:11
 * @Description:
 */

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @GetMapping("/example")
    public Result get(){
        Map<String ,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList(1,2,3,4,5,6,7));
        map.put("y",CollUtil.newArrayList(2,3,6,7,2,4,2));

        return Result.success(map);
    }


    //会员当年分布
    @GetMapping("/memberDistribution")
    public Result memberDistribution (){
        int spring = 0;
        int summer = 0;
        int fall = 0;
        int winter = 0;
        List<User> userList = userService.list();

        //filter+hutool 过滤 只要今年注册的用户 转换为list
        userList = userList.stream().filter(
                user -> new Integer(DateUtil.thisYear()).equals(DateUtil.year(user.getCreateTime()))
        ).collect(Collectors.toList());

        //筛选各季度人数
        for (User user:userList){
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:spring+=1;break;
                case Q2:summer+=1;break;
                case Q3:fall+=1;break;
                case Q4:winter+=1;break;
            }
        }

        //填充到map 给前端
        Map<String,Object> map = new HashMap<>();
        map.put("x",CollUtil.newArrayList("spring","summer","fall","winter"));
        map.put("y",CollUtil.newArrayList(spring,summer,fall,winter));
        return Result.success(map);
    }

}
