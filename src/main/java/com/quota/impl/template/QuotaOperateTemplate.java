package com.quota.impl.template;

import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.impl.route.QuotaOperateRoute;

import java.util.List;

public abstract class QuotaOperateTemplate implements QuotaOperateRoute {


    public QuotaOperateResponse doOperate(QuotaOperateRequest request) {
        //1.执行操作
        return operate(request);
    }

    protected QuotaOperateResponse operate(QuotaOperateRequest request) {
        return null;
    }
}
