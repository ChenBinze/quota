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
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
@Slf4j
public class AddQuotaAmountTemplate extends QuotaOperateTemplate{

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private QuotaInfoMapper quotaInfoMapper;

    @Autowired
    private QuotaFlowMapper quotaFlowMapper;

    @Override
    public QuotaOperateTypeEnum getQuotaOperateType() {
        return QuotaOperateTypeEnum.ADD;
    }

    @Override
    protected QuotaOperateResponse operate(final QuotaOperateRequest request) {
        //1.参数校验
        checkAddQuotaAmountRequest(request);

        //2.申请操作加redis分布式锁，并发控制
        //此处不引入redis，后续代码也就不操作释放锁动作了

        //3.开启事务逻辑处理
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    //3.1.锁db数据
                    QuotaInfoDO dbQuotaInfo = selectLockByUqKey(request);

                    //3.2.判断额度信息是否存在
                    if (dbQuotaInfo == null) {
                        log.warn("用户额度信息不存在，请先申请!clientId:{}, operateType:{}, currency{}"
                                , request.getClientId(), request.getOperateType(), request.getCurrency());
                        throw new QuotaException(ErrorEnum.QUOTA_NOT_EXIST.getErrorCode(),
                                ErrorEnum.QUOTA_NOT_EXIST.getErrorMsg());
                    }

                    //3.3.这里可以做额度状态判断，不同状态的额度操作权限不一样，此处做扩展，看明确需求

                    //3.4.更新额度信息数据库
                    updateQuota(request, dbQuotaInfo);
                    //3.5、新增额度流水
                    insertQuotaFlow(request);
                } catch (QuotaException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("用户发起额度增加操作异常，请排查!clientId:{}, operateType:{}, currency{}, exception:{}"
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

    private void checkAddQuotaAmountRequest(QuotaOperateRequest request) {
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
        updateQuotaInfo.setAmount(dbQuotaInfo.getAmount().add(request.getAmount()));
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
