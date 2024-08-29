package com.quota.api.reponse;

import java.io.Serializable;
import java.util.List;

public class QuotaQueryResponse implements Serializable {
    private static final long serialVersionUID = -8914129062348073790L;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    public String errorMessage;

    /**
     * 符合条件的总数量，分页查询用
     */
    public Integer totalCount;

    /**
     * 额度信息明细
     */
    public List<QuotaInfo> quotaInfoList;


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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<QuotaInfo> getQuotaInfoList() {
        return quotaInfoList;
    }

    public void setQuotaInfoList(List<QuotaInfo> quotaInfoList) {
        this.quotaInfoList = quotaInfoList;
    }
}
