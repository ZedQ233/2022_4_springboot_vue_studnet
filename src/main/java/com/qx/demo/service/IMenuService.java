package com.qx.demo.service;

import com.qx.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 千禧
 * @since 2022-10-12
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findChilMenus(String menuname);
}
