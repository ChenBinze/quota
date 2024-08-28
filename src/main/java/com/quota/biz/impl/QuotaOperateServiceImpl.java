package com.quota.biz.impl;

import com.quota.api.enums.ErrorEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.api.service.QuotaOperateService;
import com.quota.biz.exception.QuotaException;
import com.quota.biz.factory.QuotaOperateTemplateFactory;
import com.quota.biz.template.QuotaOperateTemplate;
import com.quota.biz.util.AssertUtils;
import com.quota.biz.util.QuotaOperateResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuotaOperateServiceImpl implements QuotaOperateService {

    @Autowired
    private QuotaOperateTemplateFactory quotaOperateTemplateFactory;
    @Override
    public QuotaOperateResponse operate(QuotaOperateRequest request) {

        try {
            AssertUtils.isTrue(request == null, ErrorEnum.INVALID_PARAMETER.getErrorCode(), "request is null");
            QuotaOperateTemplate quotaOperateTemplate = quotaOperateTemplateFactory
                    .route(QuotaOperateTypeEnum.getByCode(request.getOperateType()));
            if (null == quotaOperateTemplate) {
                log.warn("不支持用户[{}]该操作类型[{}]", request.getClientId(), request.getOperateType());
                throw new QuotaException(ErrorEnum.ACTION_NOT_ALLOW.getErrorCode(),
                        ErrorEnum.ACTION_NOT_ALLOW.getErrorMsg());
            }
            return quotaOperateTemplate.doOperate(request);
        } catch (QuotaException e) {
            return QuotaOperateResponseUtils.buidQuotaOperateResponse(
                    e.getErrorCode(), e.getErrorMsg());
        } catch (Exception e) {
            log.error("额度操作出现未知异常[{}]", e);
            return QuotaOperateResponseUtils.buidQuotaOperateResponse(ErrorEnum.SYSTEM_ERROR.getErrorCode(),
                    ErrorEnum.SYSTEM_ERROR.getErrorMsg());
        }
    }
}
