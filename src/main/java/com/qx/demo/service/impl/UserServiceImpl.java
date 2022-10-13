package com.qx.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qx.demo.common.Constants;
import com.qx.demo.entity.*;
import com.qx.demo.exception.ServiceException;
import com.qx.demo.mapper.RoleMapper;
import com.qx.demo.mapper.RoleMenuMapper;
import com.qx.demo.mapper.UserMapper;
import com.qx.demo.service.IMenuService;
import com.qx.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qx.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public LoginUser login(LoginUser loginUser) {
        User user= getUserInfo(loginUser);

        if(user!=null){
            BeanUtil.copyProperties(user,loginUser,true);
            //token
            String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
            loginUser.setToken(token);

            //设置用户角色菜单列表
            //获取role id
            String flag = user.getRole();
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("flag",flag);
            Role role = roleMapper.selectOne(queryWrapper);
            Integer id = role.getId();

            //获取角色id相关菜单id
            QueryWrapper<RoleMenu> menuQueryWrapper = new QueryWrapper<>();
            menuQueryWrapper.eq("role_id",id);
            List<RoleMenu> Menus = roleMenuMapper.selectList(menuQueryWrapper);
            List<Integer> menuIds = Menus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

            //查出所有1，2级菜单
            List<Menu> chilMenus = menuService.findChilMenus("");

            //筛选角色菜单
            List<Menu> roleMenus = new ArrayList<>();
            for (Menu menu : chilMenus) {
                //筛选一级菜单
                if(menuIds.contains(menu.getId())){
                    roleMenus.add(menu);
                }
                //筛选二级菜单
                List<Menu> children = menu.getChildrenMenu();
                if (children.size()>0){
                    children.removeIf(child -> !menuIds.contains(child.getId()));
                }
            }
            //菜单添加到
            loginUser.setMenus(roleMenus);

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
