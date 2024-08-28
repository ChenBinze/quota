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

@Component
public class QuotaOperateTask {

    @Autowired
    private QuotaTaskAsynTemplate quotaOperateAsynTemplate;

    @Autowired
    private QuotaTaskMapper quotaTaskMapper;

    @Scheduled(fixedDelay = 10 * DateUtils.MILLIS_PER_SECOND,initialDelay = 2 * DateUtils.MILLIS_PER_SECOND)
    public void operateQuotaAmount() {
        List<QuotaTaskDO> quotaTaskDOS = quotaTaskMapper.selectPageList(1, 0, 100);
        if (CollectionUtils.isEmpty(quotaTaskDOS)) {
            return;
        }

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

        for (Map.Entry<String, List<QuotaTaskDO>> entry : dbQuotaTaskMap.entrySet()) {
            quotaOperateAsynTemplate.executeQuotaOperate(entry.getValue());
        }


    }

}
