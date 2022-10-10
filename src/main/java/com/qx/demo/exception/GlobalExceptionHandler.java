package com.qx.demo.exception;

import com.qx.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author ZedQ
 * @date 2022年10月01日 12:57 
 * @Description: 业务异常处理器
 */

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }
}
