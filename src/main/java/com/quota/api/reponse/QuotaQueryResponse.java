package com.quota.api.reponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QuotaQueryResponse implements Serializable {
    private static final long serialVersionUID = -8914129062348073790L;

    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.clientId
     *
     * @mbg.generated
     */
    private String clientId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.quotaType
     *
     * @mbg.generated
     */
    private String quotaType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.currency
     *
     * @mbg.generated
     */
    private String currency;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.amount
     *
     * @mbg.generated
     */
    private BigDecimal amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.created
     *
     * @mbg.generated
     */
    private Date created;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quota_info.modified
     *
     * @mbg.generated
     */
    private Date modified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.id
     *
     * @return the value of quota_info.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.id
     *
     * @param id the value for quota_info.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * This method returns the value of the database column quota_info.status
     *
     * @return the value of quota_info.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.status
     *
     * @param status the value for quota_info.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quota_info.remark
     *
     * @return the value of quota_info.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quota_info.remark
     *
     * @param remark the value for quota_info.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}
