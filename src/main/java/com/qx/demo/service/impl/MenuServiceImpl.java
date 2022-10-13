package com.qx.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qx.demo.entity.Menu;
import com.qx.demo.mapper.MenuMapper;
import com.qx.demo.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 千禧
 * @since 2022-10-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findChilMenus(String menuname) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(menuname)){
            queryWrapper.like("name", menuname);
        }
        //获取一级菜单
        List<Menu> list = list(queryWrapper);
        List<Menu> collect = list.stream().filter(m -> m.getPid() == null).collect(Collectors.toList());

        //便利获取一级菜单所属二级菜单
        for (Menu menu:collect){
            List<Menu> collectChildren = list.stream().filter(menu1 -> menu1.getPid() == menu.getId()).collect(Collectors.toList());
            menu.setChildrenMenu(collectChildren);
        }
        return collect;
    }
}
