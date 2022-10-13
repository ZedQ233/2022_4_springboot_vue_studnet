package com.qx.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qx.demo.common.Constants;
import com.qx.demo.common.Result;
import com.qx.demo.entity.Dict;
import com.qx.demo.entity.Menu;
import com.qx.demo.mapper.DictMapper;
import com.qx.demo.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 千禧
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    // 新增或者更新
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu) {
        boolean b = menuService.saveOrUpdate(menu);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }


    //删除byId
    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean b = menuService.removeById(id);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }
    }

    //批量删除
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean b =  menuService.removeByIds(ids);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }

    //    根据角色名查询
    @PostMapping("/menuname/{menuname}")
    public Result findByName(@PathVariable String menuname) {
        QueryWrapper<Menu> MenuQueryWrapper = new QueryWrapper<>();
        MenuQueryWrapper.eq("name",menuname);
        return Result.success(menuService.getOne(MenuQueryWrapper));

    }


    //查询所有
    @GetMapping()
    public Result findAll(@RequestParam(defaultValue = "") String menuname) {

        List<Menu> chilMenus = menuService.findChilMenus(menuname);
        return Result.success(chilMenus);

    }

    //查询byid
    @GetMapping("/{id}")
    public Menu findOne(@PathVariable Integer id) {
        return menuService.getById(id);
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String menuname){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (!"".equals(menuname)) {
            queryWrapper.like("name", menuname);
        }

        //倒叙
//            queryWrapper.orderByDesc("id");
        Page<Menu> page = menuService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return Result.success(page);
    }

    //图标
    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constants.DICT_ICON_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }



}