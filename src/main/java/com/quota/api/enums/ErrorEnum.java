package com.quota.api.enums;

public enum ErrorEnum {
    SUCCESS("SUCCESS", "成功"),
    ACTION_NOT_ALLOW("ACTION_NOT_ALLOW", "不允许该操作"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统繁忙，请稍后再试"),
    INVALID_PARAMETER("INVALID_PARAMETER", "参数异常"),
    REPEATED_APPLY("REPEATED_APPLY", "重复额度申请"),
    QUOTA_NOT_EXIST("QUOTA_NOT_EXIST", "额度信息不存在"),
    QUOTA_NOT_ENOUGH("QUOTA_NOT_ENOUGH", "额度不足"),
    ;

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
