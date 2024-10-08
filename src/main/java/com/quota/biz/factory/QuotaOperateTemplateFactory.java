package com.quota.biz.factory;

import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.biz.template.QuotaOperateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工厂类，用来路由方法类
 */
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
