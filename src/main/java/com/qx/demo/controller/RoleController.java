package com.qx.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qx.demo.common.Constants;
import com.qx.demo.common.Result;
import com.qx.demo.entity.Role;
import com.qx.demo.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 千禧
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    // 新增或者更新
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        boolean b = roleService.saveOrUpdate(role);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }


    //删除byId
    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean b = roleService.removeById(id);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }
    }

    //批量删除
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean b =  roleService.removeByIds(ids);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }

//    根据角色名查询
    @PostMapping("/rolename/{rolename}")
    public Result findByName(@PathVariable String rolename) {
        QueryWrapper<Role> RoleQueryWrapper = new QueryWrapper<>();
        RoleQueryWrapper.eq("name",rolename);
        return Result.success(roleService.getOne(RoleQueryWrapper));

    }


    //查询所有
    @GetMapping()
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //查询byid
    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String rolename){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (!"".equals(rolename)) {
            queryWrapper.like("name", rolename);
        }

        //倒叙
//            queryWrapper.orderByDesc("id");
        Page<Role> page = roleService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return Result.success(page);
    }

    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);

        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId){
        return Result.success(roleService.getRoleMenu(roleId));
    }


}