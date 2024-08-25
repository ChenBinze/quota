package com.quota.impl;

import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.service.QuotaOperateService;
import com.quota.impl.exception.QuotaException;
import com.quota.impl.factory.QuotaOperateTemplateFactory;
import com.quota.impl.template.QuotaOperateTemplate;
import com.quota.impl.util.QuotaOperateResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotaOperateServiceImpl implements QuotaOperateService {

    @Autowired
    private QuotaOperateTemplateFactory quotaOperateTemplateFactory;
    @Override
    public QuotaOperateResponse operate(QuotaOperateRequest request) {

        try {
            QuotaOperateTemplate quotaOperateTemplate = quotaOperateTemplateFactory
                    .route(QuotaOperateTypeEnum.getByCode(request.getOperateType()));
            if (null == quotaOperateTemplate) {
//                log.error("不支持该操作类型[{}]", request.getOperateType());
//                throw new QuotaException();
            }
            return quotaOperateTemplate.doOperate(request);
        } catch (QuotaException e) {
            return QuotaOperateResponseUtils.buidQuotaOperateResponse();
        } catch (Exception e) {
            return QuotaOperateResponseUtils.buidQuotaOperateResponse();
        }
    }
}
