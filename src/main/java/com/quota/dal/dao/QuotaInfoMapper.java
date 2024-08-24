package com.quota.dal.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuotaInfoMapper {
    void delete(int id);
}
