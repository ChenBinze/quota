package com.quota.dal.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 额度操作流水表
 */
public class QuotaFlowDO {
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
     * 操作类型，apply-初始化，add-增加，subtract-扣减
     */
    private String operateType;

    /**
     * 备注
     */
    private String remark;

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
     * This method returns the value of the database column quota_flow.clientId
     *
     * @return the value of quota_flow.clientId
     *
     * @mbg.generated
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.clientId
     *
     * @param clientId the value for quota_flow.clientId
     *
     * @mbg.generated
     */
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_flow.quotaType
     *
     * @return the value of quota_flow.quotaType
     *
     * @mbg.generated
     */
    public String getQuotaType() {
        return quotaType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.quotaType
     *
     * @param quotaType the value for quota_flow.quotaType
     *
     * @mbg.generated
     */
    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType == null ? null : quotaType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_flow.currency
     *
     * @return the value of quota_flow.currency
     *
     * @mbg.generated
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.currency
     *
     * @param currency the value for quota_flow.currency
     *
     * @mbg.generated
     */
    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_flow.bizType
     *
     * @return the value of quota_flow.bizType
     *
     * @mbg.generated
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.bizType
     *
     * @param operateType the value for quota_flow.bizType
     *
     * @mbg.generated
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_flow.remark
     *
     * @return the value of quota_flow.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.remark
     *
     * @param remark the value for quota_flow.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_flow.created
     *
     * @return the value of quota_flow.created
     *
     * @mbg.generated
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.created
     *
     * @param created the value for quota_flow.created
     *
     * @mbg.generated
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_flow.modified
     *
     * @return the value of quota_flow.modified
     *
     * @mbg.generated
     */
    public Date getModified() {
        return modified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_flow.modified
     *
     * @param modified the value for quota_flow.modified
     *
     * @mbg.generated
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}