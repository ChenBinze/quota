package com.quota.api.enums;

public enum QuotaStatusEnum {
    FREEZE("freeze", "冻结"),
    NORMAL("normal", "正常"),
    CANCEL("cancel", "注销");

    private String code;

    private String msg;

    QuotaStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static QuotaStatusEnum getByCode(String code) {
        for (QuotaStatusEnum quotaStatusEnum : QuotaStatusEnum.values()) {
            if (quotaStatusEnum.getCode() == code) {
                return quotaStatusEnum;
            }
        }
        return null;
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
