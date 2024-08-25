package com.quota.api.reponse;

import java.io.Serializable;

public class QuotaOperateResponse implements Serializable {
    private static final long serialVersionUID = 107075847533425409L;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    public String errorMessage;

    /**
     * 扩展字段：备注
     */
    public String remark;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
