package com.quota.biz.util;

import com.quota.api.reponse.QuotaInfo;
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

    public static List<QuotaInfo> convert(List<QuotaInfoDO> quotaInfoDOList) {
        if (CollectionUtils.isEmpty(quotaInfoDOList)) {
            return null;
        }
        List<QuotaInfo> quotaInfoList = new ArrayList<>();
        for (QuotaInfoDO quotaInfoDO : quotaInfoDOList) {
            QuotaInfo quotaInfo = new QuotaInfo();
            quotaInfo.setId(quotaInfoDO.getId());
            quotaInfo.setClientId(quotaInfoDO.getClientId());
            quotaInfo.setQuotaType(quotaInfoDO.getQuotaType());
            quotaInfo.setCurrency(quotaInfoDO.getCurrency());
            quotaInfo.setAmount(quotaInfoDO.getAmount());
            quotaInfo.setStatus(quotaInfoDO.getStatus());
            quotaInfo.setRemark(quotaInfoDO.getRemark());
            quotaInfo.setCreated(quotaInfoDO.getCreated());
            quotaInfo.setModified(quotaInfoDO.getModified());
            quotaInfoList.add(quotaInfo);
        }
        return quotaInfoList;
    }

}
