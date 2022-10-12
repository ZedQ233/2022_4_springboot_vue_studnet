package com.qx.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qx.demo.common.Result;
import com.qx.demo.entity.Role;
import com.qx.demo.entity.RoleMenu;
import com.qx.demo.mapper.RoleMapper;
import com.qx.demo.mapper.RoleMenuMapper;
import com.qx.demo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 千禧
 * @since 2022-10-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        //删除所有绑定关系
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);

        //添加新的绑定菜单关系
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }

    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<RoleMenu> roleMenu = roleMenuMapper.selectList(queryWrapper);
        return roleMenu.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }
}
