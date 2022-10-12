package com.qx.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZedQ
 * @Date: 2022/10/4 16:48
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict")
public class Dict {

    private String name;
    private String value;
    private String type;
}
