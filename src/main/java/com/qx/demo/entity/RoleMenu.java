package com.qx.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: ZedQ
 * @Date: 2022/10/12 20:32
 * @Description:
 */
@TableName("sys_role_menu")
@Data
public class RoleMenu {

    private Integer roleId;
    private Integer menuId;
}
