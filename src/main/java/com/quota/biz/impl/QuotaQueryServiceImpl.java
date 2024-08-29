package com.quota.biz.impl;

import com.alibaba.fastjson2.JSON;
import com.quota.api.enums.ErrorEnum;
import com.quota.api.reponse.QuotaQueryResponse;
import com.quota.api.request.QuotaQueryRequest;
import com.quota.api.service.QuotaQueryService;
import com.quota.biz.exception.QuotaException;
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
    public QuotaQueryResponse queryQuotaDetails(QuotaQueryRequest quotaQueryRequest) {
        log.info("接收到额度查询请求QuotaQueryRequest[{}]", JSON.toJSON(quotaQueryRequest));
        try {
            //1、校验请求参数
            checkQuotaQueryRequest(quotaQueryRequest);
            List<QuotaInfoDO> dbQuotaInfos = null;
            Integer totalCount = null;
            //2、判断走分页逻辑还是全量查询逻辑
            if (isPageQuery(quotaQueryRequest.getPage(), quotaQueryRequest.getPageSize())) {
                QuotaInfoDO quotaInfoDO = ConvertUtils.convert(quotaQueryRequest);
                totalCount = quotaInfoMapper.selectCount(quotaInfoDO);
                Integer start = (quotaQueryRequest.getPage()-1) * quotaQueryRequest.getPageSize();
                dbQuotaInfos = quotaInfoMapper.selectPageList(quotaInfoDO
                        , start, quotaQueryRequest.getPageSize());
            } else {
                dbQuotaInfos = quotaInfoMapper.selectList(ConvertUtils.convert(quotaQueryRequest));
            }
            QuotaQueryResponse quotaQueryResponse = buildByErrorCode(ErrorEnum.SUCCESS.getErrorCode()
                    , ErrorEnum.SUCCESS.getErrorMsg());
            quotaQueryResponse.setQuotaInfoList(ConvertUtils.convert(dbQuotaInfos));
            quotaQueryResponse.setTotalCount(totalCount);
            return quotaQueryResponse;
        } catch (QuotaException e) {
            return buildByErrorCode(e.getErrorCode(), e.getErrorMsg());
        } catch (Exception e) {
            log.error("额度操作出现未知异常[{}]", e);
            return buildByErrorCode(ErrorEnum.SYSTEM_ERROR.getErrorCode(),
                    ErrorEnum.SYSTEM_ERROR.getErrorMsg());
        }
    }

    private boolean isPageQuery(Integer page, Integer pageSize) {
        if (page != null && page > 0 && pageSize != null && pageSize > 0) {
            return true;
        }
        return false;
    }

    private void checkQuotaQueryRequest(QuotaQueryRequest quotaQueryRequest) {
        AssertUtils.isTrue(quotaQueryRequest == null, ErrorEnum.INVALID_PARAMETER.getErrorCode()
                , "request is null");
        //额度信息比较敏感，出于信息安全考虑，查询必须指定用户id，不能全量查询
        AssertUtils.isTrue(StringUtils.isBlank(quotaQueryRequest.getClientId()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"clientId is null");
    }


    private QuotaQueryResponse buildByErrorCode(String errorCode, String errorMessage) {
        QuotaQueryResponse quotaQueryResponse = new QuotaQueryResponse();
        quotaQueryResponse.setErrorCode(errorCode);
        quotaQueryResponse.setErrorMessage(errorMessage);
        return quotaQueryResponse;
    }

}
