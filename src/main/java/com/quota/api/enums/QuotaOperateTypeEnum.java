package com.quota.api.enums;

/**
 * 额度操作类型
 */
public enum QuotaOperateTypeEnum {

    APPLY("APPLY", "额度申请"),
    ADD("ADD", "额度增加"),
    SUBTRACT("SUBTRACT", "额度扣减");

    private String code;

    private String msg;

    QuotaOperateTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static QuotaOperateTypeEnum getByCode(String code) {
        for (QuotaOperateTypeEnum quotaOperateTypeEnum : QuotaOperateTypeEnum.values()) {
            if (quotaOperateTypeEnum.getCode() == code) {
                return quotaOperateTypeEnum;
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
