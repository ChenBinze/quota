package com.quota.dal.dao;

import com.quota.dal.pojo.QuotaInfoDO;

public interface QuotaInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quota_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quota_info
     *
     * @mbg.generated
     */
    int insert(QuotaInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quota_info
     *
     * @mbg.generated
     */
    int insertSelective(QuotaInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quota_info
     *
     * @mbg.generated
     */
    QuotaInfoDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quota_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(QuotaInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quota_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(QuotaInfoDO record);
}