package com.quota.test;

import com.alibaba.fastjson2.JSON;
import com.quota.QuotaApplication;
import com.quota.api.enums.CurrencyEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.enums.QuotaTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.reponse.QuotaQueryResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.request.QuotaQueryRequest;
import com.quota.api.service.QuotaOperateService;
import com.quota.api.service.QuotaQueryService;
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
public class QuotaQueryTest {

    @Autowired
    private QuotaQueryService quotaQueryService;

    @Autowired
    private QuotaOperateService quotaOperateService;

    @Test
    public void QuotaQuery() {
        //初始化额度1
        QuotaOperateRequest quotaOperateRequest1 = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest1.setClientId(clientId);
        quotaOperateRequest1.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest1.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest1.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest1.setAmount(new BigDecimal("0"));
        QuotaOperateResponse quotaOperateResponse1 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度1申请结果:"+ clientId +  ":" + quotaOperateResponse1.getErrorCode()
                + ":" + quotaOperateResponse1.getErrorMessage());

        //初始化同clientId额度2：不同QuotaType
        quotaOperateRequest1.setClientId(clientId);
        quotaOperateRequest1.setQuotaType(QuotaTypeEnum.INSTALLMENT.getCode());
        quotaOperateRequest1.setAmount(new BigDecimal("100"));
        QuotaOperateResponse quotaOperateResponse2 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度2申请结果:"+ clientId +  ":" + quotaOperateResponse2.getErrorCode()
                + ":" + quotaOperateResponse2.getErrorMessage());

        QuotaQueryRequest quotaQueryRequest = new QuotaQueryRequest();
        quotaQueryRequest.setClientId(clientId);
        QuotaQueryResponse quotaQueryResponse = quotaQueryService.queryQuotaDetails(quotaQueryRequest);
        System.out.println("查询返回:" + JSON.toJSONString(quotaQueryResponse));
    }

    @Test
    public void QuotaQueryPage() {
        //初始化额度1
        QuotaOperateRequest quotaOperateRequest1 = new QuotaOperateRequest();
        String clientId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest1.setClientId(clientId);
        quotaOperateRequest1.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest1.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest1.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest1.setAmount(new BigDecimal("0"));
        QuotaOperateResponse quotaOperateResponse1 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度1申请结果:"+ clientId +  ":" + quotaOperateResponse1.getErrorCode()
                + ":" + quotaOperateResponse1.getErrorMessage());

        //初始化同clientId额度2：不同QuotaType
        quotaOperateRequest1.setClientId(clientId);
        quotaOperateRequest1.setQuotaType(QuotaTypeEnum.INSTALLMENT.getCode());
        quotaOperateRequest1.setAmount(new BigDecimal("100"));
        QuotaOperateResponse quotaOperateResponse2 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度2申请结果:"+ clientId +  ":" + quotaOperateResponse2.getErrorCode()
                + ":" + quotaOperateResponse2.getErrorMessage());

        //初始化同clientId额度3：不同Currency
        quotaOperateRequest1.setClientId(clientId);
        quotaOperateRequest1.setCurrency(CurrencyEnum.USD.getCode());
        quotaOperateRequest1.setAmount(new BigDecimal("100"));
        QuotaOperateResponse quotaOperateResponse3 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度2申请结果:"+ clientId +  ":" + quotaOperateResponse3.getErrorCode()
                + ":" + quotaOperateResponse3.getErrorMessage());

        QuotaQueryRequest quotaQueryRequest = new QuotaQueryRequest();
        quotaQueryRequest.setClientId(clientId);
        quotaQueryRequest.setPage(2);
        quotaQueryRequest.setPageSize(2);
        QuotaQueryResponse quotaQueryResponse = quotaQueryService.queryQuotaDetails(quotaQueryRequest);
        System.out.println("查询返回:" + JSON.toJSONString(quotaQueryResponse));
    }

    @Test
    public void invalidParameter() {

        QuotaQueryResponse quotaQueryResponse = quotaQueryService.queryQuotaDetails(null);
        System.out.println("传null查询返回:" + JSON.toJSONString(quotaQueryResponse));

        QuotaQueryRequest quotaQueryRequest = new QuotaQueryRequest();
        QuotaQueryResponse quotaQueryResponse1 = quotaQueryService.queryQuotaDetails(quotaQueryRequest);
        System.out.println("无clientId查询返回:" + JSON.toJSONString(quotaQueryResponse1));
    }
}
