package com.quota.test;

import com.quota.QuotaApplication;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.service.QuotaOperateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuotaApplication.class)
public class RouteTest {

    @Autowired
    private QuotaOperateService quotaOperateService;

    /**
     * 路由层字段异常拦截
     */
    @Test
    public void invalidParameterReqeust() {
        //reqeust为null
        QuotaOperateResponse response1 = quotaOperateService.operate(null);
        System.out.println("额度操作结果1:"+ response1.getErrorCode() + ":" + response1.getErrorMessage());

        //验证QuotaOperateType为null
        QuotaOperateRequest request2 = new QuotaOperateRequest();
        QuotaOperateResponse response2 = quotaOperateService.operate(request2);
        System.out.println("额度操作结果2:"+ response2.getErrorCode() + ":" + response2.getErrorMessage());

        //验证QuotaOperateType非预期值
        QuotaOperateRequest request3 = new QuotaOperateRequest();
        request3.setOperateType("111");
        QuotaOperateResponse response3 = quotaOperateService.operate(request3);
        System.out.println("额度操作结果3:"+ response3.getErrorCode() + ":" + response3.getErrorMessage());
    }

}
