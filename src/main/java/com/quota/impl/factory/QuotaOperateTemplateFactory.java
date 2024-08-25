package com.quota.impl.factory;

import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.impl.template.QuotaOperateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuotaOperateTemplateFactory {

    @Autowired
    private List<QuotaOperateTemplate> quotaOperateTemplateList;

    public QuotaOperateTemplate route (QuotaOperateTypeEnum quotaOperateType) {
        for (QuotaOperateTemplate quotaOperateTemplate : quotaOperateTemplateList) {
            if (quotaOperateTemplate.getQuotaOperateType().equals(quotaOperateType)) {
                return quotaOperateTemplate;
            }
        }
        return null;
    }

}
