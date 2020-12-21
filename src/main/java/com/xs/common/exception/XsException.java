package com.xs.common.exception;

/**
 * 自定义异常
 *
 * @author 18871430207@163.com
 */
public class XsException extends RuntimeException {

    public XsException() {
        super();
    }

    public XsException(String message) {
        super(message);
    }

    public XsException(String message, Throwable cause) {
        super(message, cause);
    }

    public XsException(Throwable cause) {
        super(cause);
    }

}
