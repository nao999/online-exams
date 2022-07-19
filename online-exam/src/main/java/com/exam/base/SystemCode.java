package com.exam.base;

/**
 * @ClassName : SystemCode
 * @Description :
 * @Author : y
 * @Date: 2021-07-11 08:17
 */


public enum SystemCode {

    /**
     * OK
     */
    OK(1, "成功"),

    /**
     * 登录失败
     */
    LOGINERROR(400,"登录失败"),

    /**
     * InnerError
     */
    InnerError(500, "系统内部错误"),

    USERNAME_ALREADYEXIST(600,"用户名已存在"),
    UPLOADERR(601,"上传失败"),
    QUESTION_EXIST(602,"错题本中已存在该题目"),
    OLD_PASSWORD_ERROR(603,"原始密码错误")
    ;

    /**
     * The Code.
     */
    int code;
    /**
     * The Message.
     */
    String message;

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

    SystemCode() {
    }

    SystemCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
