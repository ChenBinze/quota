package com.quota.biz.exception;

/**
 * 自定义业务异常类
 */
public class QuotaException extends RuntimeException {
    private static final long serialVersionUID = -9161822799015362271L;

    private String errorCode;

    private String errorMsg;

    public QuotaException (String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public QuotaException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
