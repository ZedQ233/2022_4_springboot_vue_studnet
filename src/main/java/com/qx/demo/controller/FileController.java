package com.qx.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qx.demo.common.Constants;
import com.qx.demo.common.Result;
import com.qx.demo.entity.Files;
import com.qx.demo.entity.User;
import com.qx.demo.service.IFileService;
import com.qx.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author ZedQ
 * @date 2022年10月04日 16:06 
 * @Description: 文件上传接口
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private IFileService fileService;

    //上传
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        //获取type
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //目录不存在就生成
        File uploadFile = new File(fileUploadPath);
        if(!uploadFile.exists()){
            uploadFile.mkdirs();
        }

        //生成uuid组成新文件名
        String uuid = IdUtil.fastSimpleUUID();
        File file1 = new File(fileUploadPath + uuid + StrUtil.DOT + type);
        String url;

        //查询md5是否存在
        String md5 = SecureUtil.md5(file.getInputStream());
        Files files = fileService.getByMd5(md5);
        if(files != null){
            url = files.getUrl();
        }else {
            url = "http://localhost:9090/file/"+uuid + StrUtil.DOT + type;
            //获取到的新文件进行存储
            file.transferTo(file1);
        }

        //对文件进行数据库存储
        Files sqlFile = new Files();
        sqlFile.setName(originalFilename);
        sqlFile.setType(type);
        sqlFile.setSize(size/1024);
        sqlFile.setUrl(url);
        sqlFile.setMd5(md5);
        fileService.save(sqlFile);
        return url;
    }

    //下载
    @GetMapping("/{uuid}")
    public void download(@PathVariable String uuid, HttpServletResponse response) throws IOException {

        File uploadFile = new File(fileUploadPath +uuid);

        //设置输出流格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uuid, "UTF-8"));
        response.setContentType("application/octet-stream");


        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }

    //分页
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        //倒叙
//            queryWrapper.orderByDesc("id");
        return Result.success(fileService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean b = fileService.removeById(id);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean b =  fileService.removeByIds(ids);
        if(b){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"服务器错误");
        }

    }

    @PostMapping("/update")
    public Result updateEnable(@RequestBody Files files){
        boolean b = fileService.updateById(files);
        if(b){
            return Result.success(b);
        }else {
            return Result.error();
        }

    }




}
