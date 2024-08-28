package com.quota.dal.mapper;

import com.quota.dal.pojo.QuotaTaskDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuotaTaskMapper {

    List<QuotaTaskDO> selectPageList(@Param("retryCount") int retryCount, @Param("start") int start,
                                     @Param("pageSize") int pageSize);

    void deleteByTaskId(@Param("taskId") String taskId);

    void updateRetryCountByTaskId(@Param("taskId") String taskId, @Param("retryCount") Integer retryCount, @Param("oriRetryCount") Integer oriRetryCount);
}
