package com.qx.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qx.demo.common.Constants;
import com.qx.demo.entity.LoginUser;
import com.qx.demo.entity.User;
import com.qx.demo.exception.ServiceException;
import com.qx.demo.mapper.UserMapper;
import com.qx.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qx.demo.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 千禧
 * @since 2022-09-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public LoginUser login(LoginUser loginUser) {
        User user= getUserInfo(loginUser);

        if(user!=null){
            BeanUtil.copyProperties(user,loginUser,true);
            //token
            String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
            loginUser.setToken(token);
            return loginUser;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户或密码错误");
        }
    }

    @Override
    public User register(LoginUser loginUser) {
        User user= getUserInfo(loginUser);
        if(user == null){
            user = new User();
            BeanUtil.copyProperties(loginUser,user,true);
            save(user);
        }else {
            throw  new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return user;

    }

    private User getUserInfo(LoginUser loginUser){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginUser.getUsername());
        queryWrapper.eq("password",loginUser.getPassword());
        User user;
        try {
            user = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return user;
    }
}
