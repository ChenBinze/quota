package com.quota.test;

import com.quota.QuotaApplication;
import com.quota.api.enums.CurrencyEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.enums.QuotaTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.service.QuotaOperateService;
import com.quota.dal.mapper.QuotaTaskMapper;
import com.quota.dal.pojo.QuotaTaskDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.Thread.sleep;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuotaApplication.class)
public class ScheduleTest {

    @Autowired
    private QuotaOperateService quotaOperateService;

    @Autowired
    private QuotaTaskMapper quotaTaskMapper;

    @Test
    public void testSchedule() throws InterruptedException {
        //初始化额度1
        QuotaOperateRequest quotaOperateRequest1 = new QuotaOperateRequest();
        String clientId1 = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest1.setClientId(clientId1);
        quotaOperateRequest1.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
        quotaOperateRequest1.setOperateType(QuotaOperateTypeEnum.APPLY.getCode());
        quotaOperateRequest1.setCurrency(CurrencyEnum.CNY.getCode());
        quotaOperateRequest1.setAmount(new BigDecimal("0"));
        QuotaOperateResponse quotaOperateResponse1 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度1申请结果:"+ clientId1 +  ":" + quotaOperateResponse1.getErrorCode()
                + ":" + quotaOperateResponse1.getErrorMessage());

        //初始化额度2
        String clientId2 = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        quotaOperateRequest1.setClientId(clientId2);
        quotaOperateRequest1.setAmount(new BigDecimal("100"));
        QuotaOperateResponse quotaOperateResponse2 = quotaOperateService.operate(quotaOperateRequest1);
        System.out.println("额度1申请结果:"+ clientId2 +  ":" + quotaOperateResponse2.getErrorCode()
                + ":" + quotaOperateResponse2.getErrorMessage());

        //新增任务多笔，额度增加，对应初始化额度1
        for (int i = 0; i < 3; i++) {
            QuotaTaskDO quotaTaskDO = new QuotaTaskDO();
            quotaTaskDO.setTaskId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                    + new Random().nextInt(999999999));
            quotaTaskDO.setAmount(new BigDecimal("10"));
            quotaTaskDO.setClientId(clientId1);
            quotaTaskDO.setCurrency(CurrencyEnum.CNY.getCode());
            quotaTaskDO.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
            quotaTaskDO.setRetryCount(0);
            quotaTaskDO.setOperateType(QuotaOperateTypeEnum.ADD.getCode());
            quotaTaskMapper.insertSelective(quotaTaskDO);
        }

        //新增任务多笔，额度扣减，对应初始化额度2
        for (int i = 0; i < 3; i++) {
            QuotaTaskDO quotaTaskDO = new QuotaTaskDO();
            quotaTaskDO.setTaskId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                    + new Random().nextInt(999999999));
            quotaTaskDO.setAmount(new BigDecimal("10"));
            quotaTaskDO.setClientId(clientId2);
            quotaTaskDO.setCurrency(CurrencyEnum.CNY.getCode());
            quotaTaskDO.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
            quotaTaskDO.setRetryCount(0);
            quotaTaskDO.setOperateType(QuotaOperateTypeEnum.SUBTRACT.getCode());
            quotaTaskMapper.insertSelective(quotaTaskDO);
        }

        //新增任务多笔，找不到额度信息，做retryCount + 1
        for (int i = 0; i < 3; i++) {
            QuotaTaskDO quotaTaskDO = new QuotaTaskDO();
            quotaTaskDO.setTaskId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                    + new Random().nextInt(999999999));
            quotaTaskDO.setAmount(new BigDecimal("10"));
            quotaTaskDO.setClientId("11111111111111");
            quotaTaskDO.setCurrency(CurrencyEnum.CNY.getCode());
            quotaTaskDO.setQuotaType(QuotaTypeEnum.CREDITCARD.getCode());
            quotaTaskDO.setRetryCount(0);
            quotaTaskDO.setOperateType(QuotaOperateTypeEnum.SUBTRACT.getCode());
            quotaTaskMapper.insertSelective(quotaTaskDO);
        }

        sleep(15000);

    }
}
