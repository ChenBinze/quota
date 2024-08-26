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
//        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("100"));
        quotaOperateRequest.setRemark("初始化");
        QuotaOperateResponse quotaOperateResponse = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("额度申请结果:"+ quotaOperateResponse.getErrorCode() + ":" + quotaOperateResponse.getErrorMessage());
//
//        QuotaOperateRequest quotaOperateRequest1 = new QuotaOperateRequest();
//        quotaOperateRequest1.setClientId(clientId);
//        quotaOperateRequest1.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
//        quotaOperateRequest1.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
//        quotaOperateRequest1.setCurrency(CurrencyEnum.CNY.getCode());
//        quotaOperateRequest1.setAmount(new BigDecimal("100"));
//        quotaOperateRequest1.setRemark("初始化");
//        QuotaOperateResponse quotaOperateResponse1 = quotaOperateService.operate(quotaOperateRequest1);
//        System.out.println("额度申请结果:"+ quotaOperateResponse1.getErrorCode() + ":" + quotaOperateResponse1.getErrorMessage());
    }

    @Test
    public void add() {
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        quotaOperateRequest.setClientId("20240826083338435");
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.ADD.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("100"));
        QuotaOperateResponse quotaOperateResponse = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("额度增加结果:"+ quotaOperateResponse.getErrorCode() + ":" + quotaOperateResponse.getErrorMessage());
    }

    @Test
    public void subtract() {
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        quotaOperateRequest.setClientId("20240826083338435");
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.SUBTRACT.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("20"));
        QuotaOperateResponse quotaOperateResponse = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("额度增加结果:"+ quotaOperateResponse.getErrorCode() + ":" + quotaOperateResponse.getErrorMessage());
    }
}
