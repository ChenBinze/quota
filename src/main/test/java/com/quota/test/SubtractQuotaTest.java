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
public class SubtractQuotaTest {
    @Autowired
    private QuotaOperateService quotaOperateService;

    /**
     * 金额扣减操作
     * 金额为空、等于0、小于0：失败
     * 金额大于0并大于数据库余额:成功
     * 金额大于0但小于等于数据库余额:成功
     */
    @Test
    public void subtract() {

        //初始化一个额度
        QuotaOperateRequest applyRequest = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        applyRequest.setClientId(clientId);
        applyRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        applyRequest.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        applyRequest.setCurrency(CurrencyEnum.CNY.getCode());
        applyRequest.setAmount(new BigDecimal("100"));
        QuotaOperateResponse applyResponse = quotaOperateService.operate(applyRequest);

        //额度初始化后操作额度扣减
        if (StringUtils.equals(applyResponse.getErrorCode(), ErrorEnum.SUCCESS.getErrorCode())) {
            //对初始化的
            QuotaOperateRequest addRequest = new QuotaOperateRequest();
            addRequest.setClientId(clientId);
            addRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
            addRequest.setOperateType(QuotaOperateTypeEnum.SUBTRACT.getCode());
            addRequest.setCurrency(CurrencyEnum.CNY.getCode());
            addRequest.setAmount(new BigDecimal("10"));//金额为空、等于0、小于0，大于0 & 大于100，大于0 & 小于等于100
            QuotaOperateResponse addResponse = quotaOperateService.operate(addRequest);
            System.out.println("额度扣减结果:"+ addResponse.getErrorCode() + ":" + addResponse.getErrorMessage());
        }
    }

    /**
     * 额度增加找不到额度信息
     */
    @Test
    public void quotaNotExist() {
        QuotaOperateRequest addRequest = new QuotaOperateRequest();
        addRequest.setClientId("111");
        addRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        addRequest.setOperateType(QuotaOperateTypeEnum.SUBTRACT.getCode());
        addRequest.setCurrency(CurrencyEnum.CNY.getCode());
        addRequest.setAmount(new BigDecimal("100"));
        QuotaOperateResponse addResponse = quotaOperateService.operate(addRequest);
        System.out.println("额度扣减结果:"+ addResponse.getErrorCode() + ":" + addResponse.getErrorMessage());
    }

    @Test
    public void invalidParameter() {
        QuotaOperateRequest quotaOperateRequest = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setOperateType(QuotaOperateTypeEnum.SUBTRACT.getCode());
        quotaOperateRequest.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest.setAmount(new BigDecimal("100"));

        //clientId is null
        quotaOperateRequest.setClientId(null);
        QuotaOperateResponse retryResponse1 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("clientId不传结果["+ retryResponse1.getErrorCode() + ":" + retryResponse1.getErrorMessage() + "]");

        //quotaType is null
        quotaOperateRequest.setClientId(clientId);
        quotaOperateRequest.setQuotaType(null);
        QuotaOperateResponse retryResponse2 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("quotaType不传结果["+ retryResponse2.getErrorCode() + ":" + retryResponse2.getErrorMessage() + "]");

        //currency is null
        quotaOperateRequest.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest.setCurrency(null);
        QuotaOperateResponse retryResponse4 = quotaOperateService.operate(quotaOperateRequest);
        System.out.println("currency不传结果["+ retryResponse4.getErrorCode() + ":" + retryResponse4.getErrorMessage() + "]");

    }
}

