package com.xs.common.entity;

/**
 * 接口结果返回的对象
 *
 * @author 18871430207@163.com
 */
public class CodeMsg {

    /** 常量key */
    private String key;
    /** 返回状态编码 */
    private String code;
    /** 返回信息 */
    private String msg;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

}
