package com.quota.api.service;

import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;

public interface QuotaOperateService {

    /**
     * 额度操作接口
     * 初始化、新增额度、扣减额度
     * @param request
     * @return
     */
    QuotaOperateResponse operate(QuotaOperateRequest request);

}
