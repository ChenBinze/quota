package com.quota.impl.template;

import com.quota.api.enums.*;
import com.quota.api.reponse.QuotaOperateResponse;
import com.quota.api.request.QuotaOperateRequest;
import com.quota.dal.dao.QuotaInfoMapper;
import com.quota.dal.pojo.QuotaInfoDO;
import com.quota.impl.exception.QuotaException;
import com.quota.impl.util.AssertUtils;
import com.quota.impl.util.QuotaOperateResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class ApplyQuotaTemplate extends QuotaOperateTemplate{

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private QuotaInfoMapper quotaInfoMapper;

    @Override
    public QuotaOperateTypeEnum getQuotaOperateType() {
        return QuotaOperateTypeEnum.APPLY;
    }

    @Override
    protected QuotaOperateResponse operate(QuotaOperateRequest request) {
        //1.参数校验
        checkApplyRequest(request);

        //2.申请操作加redis分布式锁，并发控制
        //此处不引入redis，后续代码也就不操作释放锁动作了

        //3.开启事务逻辑处理
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    List<QuotaInfoDO> dbList = selectList(request);
                    if (!CollectionUtils.isEmpty(dbList)) {
                        log.warn("用户额度信息已存在，请勿重复申请!", request.getClientId(),
                                request.getOperateType(), request.getCurrency());
                        throw new QuotaException(ErrorEnum.REPEATED_APPLY.getErrorCode(),
                                ErrorEnum.REPEATED_APPLY.getErrorMsg());
                    }
                    insert(request);
                } catch (QuotaException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("用户发起额度申请其他异常，请排查!",  request.getClientId(),
                            request.getOperateType(), request.getCurrency(), e);
                    throw new QuotaException(ErrorEnum.SYSTEM_ERROR.getErrorCode(),
                            ErrorEnum.SYSTEM_ERROR.getErrorMsg());
                }
            }
        });
        return QuotaOperateResponseUtils.buidQuotaOperateResponse(ErrorEnum.SUCCESS.getErrorCode(),
                ErrorEnum.SUCCESS.getErrorMsg());
    }

    private void checkApplyRequest(QuotaOperateRequest request) {
        AssertUtils.isTrue(request == null, ErrorEnum.INVALID_PARAMETER.getErrorCode(), "请求为空");
        AssertUtils.isTrue(StringUtils.isBlank(request.getClientId()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"用户id为空");
        AssertUtils.isTrue(StringUtils.isBlank(request.getQuotaType()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"额度类型为空");
        AssertUtils.isTrue(StringUtils.isBlank(request.getCurrency()),
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"币种为空");
        AssertUtils.isTrue(QuotaTypeEnum.getByCode(request.getQuotaType()) == null,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"额度类型不在允许范围内");
        AssertUtils.isTrue(CurrencyEnum.getByCode(request.getCurrency()) == null,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"币种类型不在允许范围内");
        AssertUtils.isTrue(request.getAmount() != null && request.getAmount().compareTo(BigDecimal.ZERO) < 0,
                ErrorEnum.INVALID_PARAMETER.getErrorCode(),"金额异常");
    }

    private List<QuotaInfoDO> selectList(QuotaOperateRequest request) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(request.getClientId());
        quotaInfoDO.setQuotaType(request.getQuotaType());
        quotaInfoDO.setCurrency(request.getCurrency());
        return quotaInfoMapper.selectList(quotaInfoDO);
    }

    private void insert(QuotaOperateRequest request) {
        QuotaInfoDO quotaInfoDO = new QuotaInfoDO();
        quotaInfoDO.setClientId(request.getClientId());
        quotaInfoDO.setQuotaType(request.getQuotaType());
        quotaInfoDO.setCurrency(request.getCurrency());
        quotaInfoDO.setAmount(request.getAmount() == null ? BigDecimal.ZERO : request.getAmount());
        quotaInfoDO.setStatus(QuotaStatusEnum.NORMAL.getCode());
        quotaInfoDO.setRemark(request.getRemark());
        quotaInfoMapper.insertSelective(quotaInfoDO);
    }
}
