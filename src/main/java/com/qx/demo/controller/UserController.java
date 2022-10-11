package com.qx.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qx.demo.common.Constants;
import com.qx.demo.common.Result;
import com.qx.demo.entity.LoginUser;
import com.qx.demo.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.servlet.http.HttpServletResponse;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;

import com.qx.demo.service.IUserService;
import com.qx.demo.entity.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 千禧
 * @since 2022-09-28
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    // 新增或者更新
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        boolean b = userService.saveOrUpdate(user);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }

    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean b = userService.removeById(id);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean b =  userService.removeByIds(ids);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }

    @PostMapping("/username/{username}")
    public Result findByName(@PathVariable String username) {
        QueryWrapper<User> UserQueryWrapper = new QueryWrapper<>();
        UserQueryWrapper.eq("username",username);
        return Result.success(userService.getOne(UserQueryWrapper));

    }

    @GetMapping()
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
            return userService.getById(id);
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        User user = TokenUtils.getCurrentUser();
        System.out.println(user.getUsername());
        System.out.println("==========="+user.toString()+"===========");
        //倒叙
//            queryWrapper.orderByDesc("id");
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return Result.success(page);
    }


//    导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list  = userService.list();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");

        writer.write(list,true);


        //设置content—type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //设置标题
        String fileName= URLEncoder.encode("用户信息","UTF-8");
        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream outputStream= response.getOutputStream();

        //将Writer刷新到OutPut
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();

    }



    //导入
    @PostMapping("/import")
    public boolean down(MultipartFile file) throws IOException {
        // 通过文件获取输入流
        InputStream is = file.getInputStream();
        // 借助hutool读取
        ExcelReader reader = ExcelUtil.getReader(is);
        List<List<Object>> list = reader.read(1); // 从第二行开始读
        // 创建一个List集合
        List<User> users = CollUtil.newArrayList();
        // 遍历
        for (List<Object> row : list) {
            User user = new User();
            // 转换成字符串
            user.setUsername(row.get(1).toString());
            user.setPassword(row.get(2).toString());
            user.setNickname(row.get(3).toString());
            user.setEmail(row.get(4).toString());
            user.setPhone(row.get(5).toString());
            user.setAddress(row.get(6).toString());
            // 遍历完一个添加一个
            users.add(user);
        }
        // 调用mapper添加到数据库
        return userService.saveBatch(users);
    }


    @PostMapping("/login")
    private Result login(@RequestBody LoginUser loginUser){
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        LoginUser login = userService.login(loginUser);
        return Result.success(login);
    }

    @PostMapping("/register")
    private Result register(@RequestBody LoginUser loginUser){
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(loginUser));
    }

}

