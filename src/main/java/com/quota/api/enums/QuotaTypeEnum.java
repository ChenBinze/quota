package com.quota.api.enums;

/**
 * 额度类型
 */
public enum QuotaTypeEnum {

    CREDITCARD("CREDITCARD", "信用卡"),
    INSTALLMENT("INSTALLMENT", "分期");

    private String code;

    private String msg;

    QuotaTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
