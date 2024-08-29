package com.quota.test;

import com.quota.QuotaApplication;
import com.quota.api.enums.CurrencyEnum;
import com.quota.api.enums.ErrorEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.enums.QuotaTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.service.QuotaOperateService;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 其他参数正常，不同金额初始化成功
     * 金额小于0：失败
     * 金额大于等于0：成功
     * 金额不传：默认为0
     */
    @Test
    public void applyForAmount() {
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("10")); //大于0，小于0，等于0，不传
        QuotaOperateResponse quotaOperateResponse = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("额度申请结果:"+ quotaOperateResponse.getErrorCode() + ":" + quotaOperateResponse.getErrorMessage());
    }

    /**
     * 已初始化额度，拒绝新的初始化请求
     */
    @Test
    public void retryApply() {
        //首次初始化
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("100"));
        QuotaOperateResponse quotaOperateResponse = quotaOperateService.operate(quotaOperateRequest);
        if (StringUtils.equals(quotaOperateResponse.getErrorCode(), ErrorEnum.SUCCESS.getErrorCode())) {
            QuotaOperateResponse retryResponse = quotaOperateService.operate(quotaOperateRequest);
            System.out.println("额度重复申请结果["+ retryResponse.getErrorCode() + ":" + retryResponse.getErrorMessage() + "]");
        }
    }

    @Test
    public void invalidParameter() {
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("100"));

        //clientId is null
        quotaOperateRequest.setClientId(null);
        QuotaOperateResponse retryResponse1 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("clientId不传申请结果["+ retryResponse1.getErrorCode() + ":" + retryResponse1.getErrorMessage() + "]");

        //quotaType is null
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(null);
        QuotaOperateResponse retryResponse2 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("quotaType不传申请结果["+ retryResponse2.getErrorCode() + ":" + retryResponse2.getErrorMessage() + "]");

        //quotaType not allowed
        quotaOperateRequest.setQuotaType("111");
        QuotaOperateResponse retryResponse3 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("quotaType传不允许值结果["+ retryResponse3.getErrorCode() + ":" + retryResponse3.getErrorMessage() + "]");


        //currency is null
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setCurrency(null);
        QuotaOperateResponse retryResponse4 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("currency不传申请结果["+ retryResponse4.getErrorCode() + ":" + retryResponse4.getErrorMessage() + "]");

        //currency not allowed
        quotaOperateRequest.setCurrency("111");
        QuotaOperateResponse retryResponse5 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("currency传不允许值结果["+ retryResponse5.getErrorCode() + ":" + retryResponse5.getErrorMessage() + "]");

    }
}
