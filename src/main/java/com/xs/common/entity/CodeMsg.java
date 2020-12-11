package com.xs.common.entity;

/**
 * 接口结果返回的对象
 *
 * @author xiaotinghao
 */
public class CodeMsg {

    /** 返回状态编码 */
    private String code;
    /** 返回信息 */
    private String msg;

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
