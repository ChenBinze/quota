package com.quota.test;

import com.quota.QuotaApplication;
import com.quota.api.enums.CurrencyEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.enums.QuotaTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.service.QuotaOperateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuotaApplication.class)
public class ApplyQuotaInfoTest {
    @Autowired
    private QuotaOperateService quotaOperateService;

    @Test
    public void apply() {
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("0"));
        quotaOperateRequest.setRemark("初始化");
        QuotaOperateResponse quotaOperateResponse = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("额度申请结果:"+ quotaOperateResponse.getErrorCode() + ":" + quotaOperateResponse.getErrorMessage());
    }
}
