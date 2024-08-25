package com.quota.api.enums;

public enum ErrorEnum {
    ACTION_NOT_ALLOW("ACTION_NOT_ALLOW", "不允许该操作"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统繁忙，请稍后再试");

    private String errorCode;

    private String errorMsg;

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String code) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
