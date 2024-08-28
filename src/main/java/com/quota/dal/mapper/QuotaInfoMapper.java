package com.quota.dal.mapper;

import com.quota.dal.pojo.QuotaInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuotaInfoMapper {

    int insertSelective(QuotaInfoDO record);

    List<QuotaInfoDO> selectList(QuotaInfoDO record);

    QuotaInfoDO selectLockByUqKey(QuotaInfoDO record);

    int updateByPrimaryKeySelective(QuotaInfoDO record);

    List<QuotaInfoDO> selectPageList(@Param("quotaInfo") QuotaInfoDO quotaInfoDO, @Param("start") int start,
                                     @Param("pageSize") int pageSize);
}