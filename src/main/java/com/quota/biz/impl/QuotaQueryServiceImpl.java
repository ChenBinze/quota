package com.quota.biz.impl;

import com.quota.api.enums.ErrorEnum;
import com.quota.api.reponse.QuotaQueryResponse;
import com.quota.api.request.QuotaQueryRequest;
import com.quota.api.service.QuotaQueryService;
import com.quota.biz.util.AssertUtils;
import com.quota.biz.util.ConvertUtils;
import com.quota.dal.mapper.QuotaInfoMapper;
import com.quota.dal.pojo.QuotaInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuotaQueryServiceImpl implements QuotaQueryService {

    @Autowired
    private QuotaInfoMapper quotaInfoMapper;

    @Override
    public List<QuotaQueryResponse> queryQuotaDetail(QuotaQueryRequest quotaQueryRequest) {
        checkQuotaQueryRequest(quotaQueryRequest);
        List<QuotaInfoDO> dbQuotaInfos = null;
        if (isPageQuery(quotaQueryRequest.getPage(), quotaQueryRequest.getPageSize())) {
            dbQuotaInfos = quotaInfoMapper.selectPageList(ConvertUtils.convert(quotaQueryRequest)
                    , quotaQueryRequest.getPage(), quotaQueryRequest.getPageSize());
        } else {
            dbQuotaInfos = quotaInfoMapper.selectList(ConvertUtils.convert(quotaQueryRequest));
        }
        return ConvertUtils.convert(dbQuotaInfos);
    }

    private boolean isPageQuery(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            return true;
        }
        return false;
    }

    private void checkQuotaQueryRequest(QuotaQueryRequest quotaQueryRequest) {
        AssertUtils.isTrue(quotaQueryRequest == null, ErrorEnum.INVALID_PARAMETER.getErrorCode()
                , "request is null");
        AssertUtils.isTrue(StringUtils.isBlank(quotaQueryRequest.getClientId()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"clientId is null");
    }

}
