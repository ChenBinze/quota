package com.quota.biz.schedule;

import com.quota.biz.template.QuotaTaskAsynTemplate;
import com.quota.dal.mapper.QuotaTaskMapper;
import com.quota.dal.pojo.QuotaTaskDO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 定时任务
 */
@Component
public class QuotaOperateTask {

    @Autowired
    private QuotaTaskAsynTemplate quotaOperateAsynTemplate;

    @Autowired
    private QuotaTaskMapper quotaTaskMapper;

    @Scheduled(fixedDelay = 10 * DateUtils.MILLIS_PER_SECOND,initialDelay = 10 * DateUtils.MILLIS_PER_SECOND)
    public void operateQuotaAmount() {
        //根据数据已重试次数对应不同周期的定时任务，这里这个定时任务只处理没操作过的任务数据
        //每次捞取100条数据，处理成功后任务数据删除，retryCount加1，走其他周期的定时任务
        //可以针对retryCount制定重试次数阀值，到达阀值后告警等处理
        List<QuotaTaskDO> quotaTaskDOS = quotaTaskMapper.selectPageList(0, 0, 100);
        if (CollectionUtils.isEmpty(quotaTaskDOS)) {
            return;
        }

        //捞取出来的数据，根据clientId + quotaType + currency分组，这里因为我设计这几个为额度的唯一约束
        //如果捞取的数据串行执行太慢；如果全部并行则因为后续要对db数据加锁操作，如果同个额度信息并发的话会导致抢锁从而出现失败
        //所以针对额度信息的唯一约束进行分组，组与组之间并行操作，同组里面的数据串行执行
        Map<String, List<QuotaTaskDO>> dbQuotaTaskMap = new HashMap<>();
        for (QuotaTaskDO quotaTaskDO : quotaTaskDOS) {
            String taskKey = new StringBuilder().append(quotaTaskDO.getClientId()).append("_")
                    .append(quotaTaskDO.getQuotaType()).append("_").append(quotaTaskDO.getCurrency()).toString();
            if (CollectionUtils.isEmpty(dbQuotaTaskMap.get(taskKey))) {
                List<QuotaTaskDO> list = new ArrayList<>();
                list.add(quotaTaskDO);
                dbQuotaTaskMap.put(taskKey, list);
            } else {
                dbQuotaTaskMap.get(taskKey).add(quotaTaskDO);
            }
        }

        //针对额度信息的唯一约束进行分组后，组与组之间并行操作，同组里面的数据串行执行
        for (Map.Entry<String, List<QuotaTaskDO>> entry : dbQuotaTaskMap.entrySet()) {
            quotaOperateAsynTemplate.executeQuotaOperate(entry.getValue());
        }


    }

}
