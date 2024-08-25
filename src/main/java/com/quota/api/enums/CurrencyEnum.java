package com.quota.api.enums;

/**
 * 币种
 */
public enum CurrencyEnum {
    CNY("156", "人民币"),
    USD("840", "美元");

    private String code;

    private String msg;

    CurrencyEnum(String code, String msg) {
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
