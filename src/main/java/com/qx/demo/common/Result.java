package com.qx.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZedQ
 * @date 2022年10月01日 12:43 
 * @Description: 接口统一返回包装
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }




}
