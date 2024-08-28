package com.quota.api.request;

import java.io.Serializable;

public class QuotaQueryRequest implements Serializable {
    private static final long serialVersionUID = 54284485950393273L;

    /**
     * 用户id
     */
    private String clientId;

    /**
     * 额度类型
     * @see com.quota.api.enums.QuotaTypeEnum
     */
    private String quotaType;

    /**
     * 币种
     * @see com.quota.api.enums.CurrencyEnum
     */
    private String currency;

    private String status;

    /**
     * 分页字段
     */
    private Integer page;

    private Integer pageSize;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
