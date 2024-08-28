package com.quota.api.service;

import com.quota.api.reponse.QuotaQueryResponse;
import com.quota.api.request.QuotaQueryRequest;

import java.util.List;

public interface QuotaQueryService {

    /**
     * 额度查询操作
     * @param quotaQueryRequest
     * @return
     */
    List<QuotaQueryResponse> queryQuotaDetail(QuotaQueryRequest quotaQueryRequest);

}
