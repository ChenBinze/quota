package com.quota.biz.util;

import com.quota.api.reponse.QuotaQueryResponse;
import com.quota.api.request.QuotaQueryRequest;
import com.quota.dal.pojo.QuotaInfoDO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtils {

    public static QuotaInfoDO convert(QuotaQueryRequest quotaQueryRequest) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(quotaQueryRequest.getClientId());
        quotaInfoDO.setQuotaType(quotaQueryRequest.getQuotaType());
        quotaInfoDO.setCurrency(quotaQueryRequest.getCurrency());
        return quotaInfoDO;
    }

    public static List<QuotaQueryResponse> convert(List<QuotaInfoDO> quotaInfoDOList) {
        if (CollectionUtils.isEmpty(quotaInfoDOList)) {
            return null;
        }
        List<QuotaQueryResponse> quotaQueryResponseList = new ArrayList<>();
        for (QuotaInfoDO quotaInfoDO : quotaInfoDOList) {
            QuotaQueryResponse quotaQueryResponse = new QuotaQueryResponse();
            quotaQueryResponse.setId(quotaInfoDO.getId());
            quotaQueryResponse.setClientId(quotaInfoDO.getClientId());
            quotaQueryResponse.setQuotaType(quotaInfoDO.getQuotaType());
            quotaQueryResponse.setCurrency(quotaInfoDO.getCurrency());
            quotaQueryResponse.setAmount(quotaInfoDO.getAmount());
            quotaQueryResponse.setStatus(quotaInfoDO.getStatus());
            quotaQueryResponse.setRemark(quotaInfoDO.getRemark());
            quotaQueryResponse.setCreated(quotaInfoDO.getCreated());
            quotaQueryResponse.setModified(quotaInfoDO.getModified());
            quotaQueryResponseList.add(quotaQueryResponse);
        }
        return quotaQueryResponseList;
    }

}
