package com.quota.impl.template;

import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.impl.route.QuotaOperateRoute;

import java.util.List;

public abstract class QuotaOperateTemplate implements QuotaOperateRoute {


    public QuotaOperateResponse doOperate(QuotaOperateRequest request) {
        //1.加redis分布式锁，并发控制
        //此处不引入redis，后续代码也就不操作释放锁动作了

        //2.执行操作
        return operate(request);
    }

    protected QuotaOperateResponse operate(QuotaOperateRequest request) {
        return null;
    }
}
