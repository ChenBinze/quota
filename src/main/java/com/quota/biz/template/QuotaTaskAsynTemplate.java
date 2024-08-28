package com.quota.biz.template;

import com.quota.api.enums.ErrorEnum;
import com.quota.api.enums.QuotaOperateTypeEnum;
import com.quota.biz.exception.QuotaException;
import com.quota.dal.mapper.QuotaFlowMapper;
import com.quota.dal.mapper.QuotaInfoMapper;
import com.quota.dal.mapper.QuotaTaskMapper;
import com.quota.dal.pojo.QuotaFlowDO;
import com.quota.dal.pojo.QuotaInfoDO;
import com.quota.dal.pojo.QuotaTaskDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@Slf4j
public class QuotaTaskAsynTemplate {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private QuotaInfoMapper quotaInfoMapper;

    @Autowired
    private QuotaFlowMapper quotaFlowMapper;

    @Autowired
    private QuotaTaskMapper quotaTaskMapper;

    @Async("quotaExecutor")
    public void executeQuotaOperate(List<QuotaTaskDO> list) {
        for (QuotaTaskDO quotaTaskDO: list) {
            try {
                doExecute(quotaTaskDO);
            } catch (QuotaException e) {
                updateRetryCount(quotaTaskDO);
            } catch (Exception e) {
                log.error("额度操作定时任务异常，QuotaTaskDO:[{}], e:[{}]", quotaTaskDO, e);
                updateRetryCount(quotaTaskDO);
            }
        }
    }

    private void doExecute(final QuotaTaskDO quotaTaskDO) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                QuotaInfoDO dbQuotaInfo = selectLockByUqKey(quotaTaskDO);
                if (dbQuotaInfo == null) {
                    log.warn("用户额度信息不存在，请先申请! clientId:{}, operateType:{}, currency:{}"
                            , quotaTaskDO.getClientId(), quotaTaskDO.getOperateType(), quotaTaskDO.getCurrency());
                    throw new QuotaException(ErrorEnum.QUOTA_NOT_EXIST.getErrorCode(),
                            ErrorEnum.QUOTA_NOT_EXIST.getErrorMsg());
                }
                updateQuota(quotaTaskDO, dbQuotaInfo);
                insertQuotaFlow(quotaTaskDO);
                quotaTaskMapper.deleteByTaskId(quotaTaskDO.getTaskId());
            }
        });
    }

    private QuotaInfoDO selectLockByUqKey(QuotaTaskDO quotaTaskDO) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(quotaTaskDO.getClientId());
        quotaInfoDO.setQuotaType(quotaTaskDO.getQuotaType());
        quotaInfoDO.setCurrency(quotaTaskDO.getCurrency());
        return quotaInfoMapper.selectLockByUqKey(quotaInfoDO);
    }

    private void updateQuota(QuotaTaskDO quotaTaskDO, QuotaInfoDO dbQuotaInfo) {
        QuotaInfoDO updateQuotaInfo = new QuotaInfoDO();
        updateQuotaInfo.setId(dbQuotaInfo.getId());
        if (StringUtils.equals(quotaTaskDO.getOperateType(), QuotaOperateTypeEnum.ADD.getCode())) {
            updateQuotaInfo.setAmount(dbQuotaInfo.getAmount().add(quotaTaskDO.getAmount()));
        } else if (StringUtils.equals(quotaTaskDO.getOperateType(), QuotaOperateTypeEnum.SUBTRACT.getCode())) {
            if (dbQuotaInfo.getAmount().compareTo(dbQuotaInfo.getAmount()) > 0) {
                log.warn("用户可用额度[{}]小于扣减请求额度[{}], QuotaTaskDO:[{}]", quotaTaskDO);
                throw new QuotaException(ErrorEnum.QUOTA_NOT_ENOUGH.getErrorCode(),
                        ErrorEnum.QUOTA_NOT_ENOUGH.getErrorMsg());
            }
            updateQuotaInfo.setAmount(dbQuotaInfo.getAmount().subtract(quotaTaskDO.getAmount()));
        } else {
            log.warn("不允许的定时任务操作, QuotaTaskDO:[{}]", quotaTaskDO);
            throw new QuotaException(ErrorEnum.ACTION_NOT_ALLOW.getErrorCode(),
                    ErrorEnum.ACTION_NOT_ALLOW.getErrorMsg());
        }
        quotaInfoMapper.updateByPrimaryKeySelective(updateQuotaInfo);
    }

    private void insertQuotaFlow(QuotaTaskDO quotaTaskDO) {
        QuotaFlowDO quotaFlowDO = new QuotaFlowDO();
        quotaFlowDO.setClientId(quotaTaskDO.getClientId());
        quotaFlowDO.setQuotaType(quotaTaskDO.getQuotaType());
        quotaFlowDO.setCurrency(quotaTaskDO.getCurrency());
        quotaFlowDO.setOperateType(quotaTaskDO.getOperateType());
        quotaFlowDO.setAmount(quotaTaskDO.getAmount());
        quotaFlowDO.setRemark("定时任务操作");
        quotaFlowMapper.insertSelective(quotaFlowDO);
    }

    private void updateRetryCount(QuotaTaskDO quotaTaskDO) {
        try {
            Integer newRetryCount = quotaTaskDO.getRetryCount() + 1;
            quotaTaskMapper.updateRetryCountByTaskId(quotaTaskDO.getTaskId(), newRetryCount, quotaTaskDO.getRetryCount());
        } catch (Exception e) {
            log.error("更新定时任务重试次数失败quotaTaskDO:[{}], exception:[{}]", quotaTaskDO, e);
        }
    }
}
