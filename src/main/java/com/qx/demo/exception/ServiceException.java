package com.qx.demo.exception;

import lombok.Getter;

/**
 * @author ZedQ
 * @date 2022年10月01日 12:59 
 * @Description: 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}