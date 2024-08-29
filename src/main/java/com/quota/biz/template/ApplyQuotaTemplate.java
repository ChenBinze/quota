package com.quota.biz.template;

import com.quota.api.enums.*;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.dal.mapper.QuotaFlowMapper;
import com.quota.dal.mapper.QuotaInfoMapper;
import com.quota.dal.pojo.QuotaFlowDO;
import com.quota.dal.pojo.QuotaInfoDO;
import com.quota.biz.exception.QuotaException;
import com.quota.biz.util.AssertUtils;
import com.quota.biz.util.QuotaOperateResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 额度申请方法类
 */
@Service
@Slf4j
public class ApplyQuotaTemplate extends QuotaOperateTemplate{

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private QuotaInfoMapper quotaInfoMapper;

    @Autowired
    private QuotaFlowMapper quotaFlowMapper;

    @Override
    public QuotaOperateTypeEnum getQuotaOperateType() {
        return QuotaOperateTypeEnum.APPLY;
    }

    @Override
    protected QuotaOperateResponse operate(final QuotaOperateRequest request) {
        //1.参数校验
        checkApplyRequest(request);

        //2.开启事务逻辑处理
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    List<QuotaInfoDO> dbList = selectList(request);
                    if (!CollectionUtils.isEmpty(dbList)) {
                        log.warn("用户额度信息已存在，请勿重复申请!, clientId:{}, operateType:{}, currency:{}"
                                , request.getClientId(), request.getOperateType(), request.getCurrency());
                        throw new QuotaException(ErrorEnum.REPEATED_APPLY.getErrorCode(),
                                ErrorEnum.REPEATED_APPLY.getErrorMsg());
                    }
                    insert(request);
                    //2.5、新增额度申请流水
                    insertQuotaFlow(request);
                } catch (QuotaException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("用户clientId:{}发起额度申请出现异常，请排查!{}",  request.getClientId(), e);
                    throw new QuotaException(ErrorEnum.SYSTEM_ERROR.getErrorCode(),
                            ErrorEnum.SYSTEM_ERROR.getErrorMsg());
                }
            }
        });
        return QuotaOperateResponseUtils.buidQuotaOperateResponse(ErrorEnum.SUCCESS.getErrorCode(),
                ErrorEnum.SUCCESS.getErrorMsg());
    }

    private void checkApplyRequest(QuotaOperateRequest request) {
        AssertUtils.isTrue(StringUtils.isBlank(request.getClientId()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"clientId is null");
        AssertUtils.isTrue(StringUtils.isBlank(request.getQuotaType()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"quotaType is null");
        AssertUtils.isTrue(StringUtils.isBlank(request.getCurrency()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"currency is null");
        AssertUtils.isTrue(QuotaTypeEnum.getByCode(request.getQuotaType()) == null,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"quotaType is not allowed");
        AssertUtils.isTrue(CurrencyEnum.getByCode(request.getCurrency()) == null,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"currency is not allowed");
        AssertUtils.isTrue(request.getAmount() != null && request.getAmount().compareTo(BigDecimal.ZERO) < 0,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"abnormal amount");
    }

    private List<QuotaInfoDO> selectList(QuotaOperateRequest request) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(request.getClientId());
        quotaInfoDO.setQuotaType(request.getQuotaType());
        quotaInfoDO.setCurrency(request.getCurrency());
        return quotaInfoMapper.selectList(quotaInfoDO);
    }

    private void insert(final QuotaOperateRequest request) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(request.getClientId());
        quotaInfoDO.setQuotaType(request.getQuotaType());
        quotaInfoDO.setCurrency(request.getCurrency());
        quotaInfoDO.setAmount(request.getAmount() == null ? BigDecimal.ZERO : request.getAmount());
        quotaInfoDO.setStatus(QuotaStatusEnum.NORMAL.getCode());
        quotaInfoDO.setRemark(request.getRemark());
        quotaInfoMapper.insertSelective(quotaInfoDO);
    }

    private void insertQuotaFlow(QuotaOperateRequest request) {
        QuotaFlowDO quotaFlowDO = new QuotaFlowDO();
        quotaFlowDO.setClientId(request.getClientId());
        quotaFlowDO.setQuotaType(request.getQuotaType());
        quotaFlowDO.setCurrency(request.getCurrency());
        quotaFlowDO.setOperateType(request.getOperateType());
        quotaFlowDO.setAmount(request.getAmount());
        quotaFlowDO.setRemark(request.getRemark());
        quotaFlowMapper.insertSelective(quotaFlowDO);
    }
}
