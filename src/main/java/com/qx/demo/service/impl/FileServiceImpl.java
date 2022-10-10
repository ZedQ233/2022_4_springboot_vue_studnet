package com.qx.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qx.demo.common.Constants;
import com.qx.demo.entity.Files;
import com.qx.demo.exception.ServiceException;
import com.qx.demo.mapper.FileMapper;
import com.qx.demo.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 千禧
 * @since 2022-09-28
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {


    @Override
    public Files getByMd5(String md5) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        Files files = null;

        try {
            List<Files> list = list(queryWrapper);
            if(list.size()!=0)
                files = list.get(0);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }

        return files;

    }
}
