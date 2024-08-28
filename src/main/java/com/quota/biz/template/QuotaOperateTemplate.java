package com.quota.biz.template;

import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.biz.route.QuotaOperateRoute;

public abstract class QuotaOperateTemplate implements QuotaOperateRoute {

    public QuotaOperateResponse doOperate(QuotaOperateRequest request) {
        //1.执行操作
        return operate(request);
    }

    protected QuotaOperateResponse operate(QuotaOperateRequest request) {
        return null;
    }
}
