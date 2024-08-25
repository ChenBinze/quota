package com.quota.impl.util;

import com.quota.api.reponse.QuotaOperateResponse;

public class QuotaOperateResponseUtils {

    public static QuotaOperateResponse buidQuotaOperateResponse(String code, String msg) {
        QuotaOperateResponse quotaOperateResponse = new QuotaOperateResponse();
        quotaOperateResponse.setErrorCode(code);
        quotaOperateResponse.setErrorMessage(msg);
        return quotaOperateResponse;
    }

}
