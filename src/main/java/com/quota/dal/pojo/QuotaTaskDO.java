package com.quota.dal.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class QuotaTaskDO {

    private String taskId;

    /**
     * 用户id
     */
    private String clientId;

    /**
     * 额度类型
     */
    private String quotaType;

    /**
     * 币种
     */
    private String currency;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date modified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.clientId
     *
     * @return the value of quota_info.clientId
     *
     * @mbg.generated
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.clientId
     *
     * @param clientId the value for quota_info.clientId
     *
     * @mbg.generated
     */
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.quotaType
     *
     * @return the value of quota_info.quotaType
     *
     * @mbg.generated
     */
    public String getQuotaType() {
        return quotaType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.quotaType
     *
     * @param quotaType the value for quota_info.quotaType
     *
     * @mbg.generated
     */
    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType == null ? null : quotaType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.currency
     *
     * @return the value of quota_info.currency
     *
     * @mbg.generated
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.currency
     *
     * @param currency the value for quota_info.currency
     *
     * @mbg.generated
     */
    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.amount
     *
     * @return the value of quota_info.amount
     *
     * @mbg.generated
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.amount
     *
     * @param amount the value for quota_info.amount
     *
     * @mbg.generated
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.created
     *
     * @return the value of quota_info.created
     *
     * @mbg.generated
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.created
     *
     * @param created the value for quota_info.created
     *
     * @mbg.generated
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.modified
     *
     * @return the value of quota_info.modified
     *
     * @mbg.generated
     */
    public Date getModified() {
        return modified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.modified
     *
     * @param modified the value for quota_info.modified
     *
     * @mbg.generated
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
