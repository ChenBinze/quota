package com.quota.impl.template;

import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;

public class AddQuotaAmountTemplate extends QuotaOperateTemplate{
    @Override
    public QuotaOperateTypeEnum getQuotaOperateType() {
        return QuotaOperateTypeEnum.ADD;
    }

    @Override
    protected QuotaOperateResponse operate(QuotaOperateRequest request) {
        return null;
    }
}
