package com.quota.biz.template;

import com.quota.api.enums.ErrorEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

/**
 * 额度扣减方法类
 */
@Component
@Slf4j
public class SubtractQuotaAmountTemplate extends QuotaOperateTemplate {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private QuotaInfoMapper quotaInfoMapper;

    @Autowired
    private QuotaFlowMapper quotaFlowMapper;

    @Override
    public QuotaOperateTypeEnum getQuotaOperateType() {
        return QuotaOperateTypeEnum.SUBTRACT;
    }

    @Override
    protected QuotaOperateResponse operate(final QuotaOperateRequest request) {
        //1.参数校验
        checkSubtractQuotaAmountRequest(request);

        //2.开启事务逻辑处理
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    //2.1 锁db数据
                    QuotaInfoDO dbQuotaInfo = selectLockByUqKey(request);

                    //2.2 判断额度信息是否存在
                    if (dbQuotaInfo == null) {
                        log.warn("用户额度信息不存在，请先申请!clientId:{}, operateType:{}, currency{}"
                                , request.getClientId(), request.getOperateType(), request.getCurrency());
                        throw new QuotaException(ErrorEnum.QUOTA_NOT_EXIST.getErrorCode(),
                                ErrorEnum.QUOTA_NOT_EXIST.getErrorMsg());
                    }

                    //2.3 判断扣减额度是否超过可用额度余额
                    if (request.getAmount().compareTo(dbQuotaInfo.getAmount()) > 0) {
                        log.warn("用户可用额度[{}]小于扣减请求额度[{}], clientId:{}, operateType:{}, currency:{}"
                                , dbQuotaInfo.getAmount(), request.getAmount(), request.getClientId()
                                , request.getOperateType(), request.getCurrency());
                        throw new QuotaException(ErrorEnum.QUOTA_NOT_ENOUGH.getErrorCode(),
                                ErrorEnum.QUOTA_NOT_ENOUGH.getErrorMsg());
                    }

                    //2.4 这里可以做额度状态判断，不同状态的额度操作权限不一样，此处做扩展，看明确需求

                    //2.5 更新额度信息数据库
                    updateQuota(request, dbQuotaInfo);

                    //2.6 新增额度流水
                    insertQuotaFlow(request);

                } catch (QuotaException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("用户发起额度扣减操作异常，请排查!clientId:{}, operateType:{}, currency{}, exception:{}"
                            , request.getClientId(), request.getOperateType()
                            , request.getCurrency(), request.getAmount(), e);
                    throw new QuotaException(ErrorEnum.SYSTEM_ERROR.getErrorCode(),
                            ErrorEnum.SYSTEM_ERROR.getErrorMsg());
                }
            }
        });
        return QuotaOperateResponseUtils.buidQuotaOperateResponse(ErrorEnum.SUCCESS.getErrorCode(),
                ErrorEnum.SUCCESS.getErrorMsg());
    }

    private void checkSubtractQuotaAmountRequest(QuotaOperateRequest request) {
        AssertUtils.isTrue(StringUtils.isBlank(request.getClientId()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"clientId is null");
        AssertUtils.isTrue(StringUtils.isBlank(request.getQuotaType()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"quotaType is null");
        AssertUtils.isTrue(StringUtils.isBlank(request.getCurrency()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"currency is null");
        AssertUtils.isTrue(request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"abnormal amount");
    }

    private QuotaInfoDO selectLockByUqKey(QuotaOperateRequest request) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(request.getClientId());
        quotaInfoDO.setQuotaType(request.getQuotaType());
        quotaInfoDO.setCurrency(request.getCurrency());
        return quotaInfoMapper.selectLockByUqKey(quotaInfoDO);
    }

    private void updateQuota(QuotaOperateRequest request, QuotaInfoDO dbQuotaInfo) {
        QuotaInfoDO updateQuotaInfo = new QuotaInfoDO();
        updateQuotaInfo.setId(dbQuotaInfo.getId());
        updateQuotaInfo.setAmount(dbQuotaInfo.getAmount().subtract(request.getAmount()));
        quotaInfoMapper.updateByPrimaryKeySelective(updateQuotaInfo);
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
