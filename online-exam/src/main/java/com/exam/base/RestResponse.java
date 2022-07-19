package com.exam.base;

import com.baomidou.mybatisplus.extension.api.R;

/**
 * @ClassName : RestResponse
 * @Description : 数据返回的格式
 * @Author : y
 * @Date: 2021-07-11 08:07
 */


public class RestResponse<T> {
    private int code;
    private String message;
    private T response;

    public RestResponse(int code,String message){
        this.code = code;
        this.message = message;
    }

    public RestResponse(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    /**
     * @Description : 返回失败信息
     * @date 2021/7/11 8:14
     * @author formh
     */
    public static <Type> RestResponse<Type> fail(Integer code, String msg) {
        return new RestResponse<Type>(code, msg);
    }

    /**
     * @Description : 成功无数据返回
     * @date 2021/7/11 8:14
     * @author formh
     */
    public static <Type> RestResponse<Type> ok() {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<Type>(systemCode.getCode(), systemCode.getMessage());
    }

    /**
     * @Description : 成功有数据返回
     * @date 2021/7/11 8:15
     * @author formh
     */
    public static <Type> RestResponse<Type> ok(Type response) {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), response);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
