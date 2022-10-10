package com.qx.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qx.demo.entity.Files;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 千禧
 * @since 2022-09-28
 */
public interface IFileService extends IService<Files> {

    Files getByMd5(String md5);

}
