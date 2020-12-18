package com.xs.common.entity;

/**
 * 接口结果返回的对象
 *
 * @author 18871430207@163.com
 */
public class Result {

    /** 常量key */
    private String code;
    /** 常量key */
    private String msg;
    /** 常量key */
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultConstants.SUCCESS, "", null);
    }

    public static Result success(String data) {
        return new Result(ResultConstants.SUCCESS, "", data);
    }

    public static Result success(String code, String data) {
        return new Result(code, "", data);
    }

    public static Result fail() {
        return new Result(ResultConstants.FAILURE, "", null);
    }

    public static Result fail(String message) {
        return new Result(ResultConstants.FAILURE, message, null);
    }

    public static Result fail(String code, String msg) {
        return new Result(code, msg, null);
    }

    class ResultConstants {
        /**
         * 接口请求成功默认编码
         */
        static final String SUCCESS = "1";
        /**
         * 接口请求失败默认编码
         */
        static final String FAILURE = "0";
    }

}
