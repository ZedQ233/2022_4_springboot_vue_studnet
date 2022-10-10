package com.qx.demo.service;

import com.qx.demo.entity.LoginUser;
import com.qx.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 千禧
 * @since 2022-09-28
 */
public interface IUserService extends IService<User> {

    LoginUser login(LoginUser loginUser);

    User register(LoginUser loginUser);
}
