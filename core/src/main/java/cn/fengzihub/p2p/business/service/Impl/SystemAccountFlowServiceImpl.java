package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.business.domain.SystemAccount;
import cn.fengzihub.p2p.business.domain.SystemAccountFlow;
import cn.fengzihub.p2p.business.mapper.SystemAccountFlowMapper;
import cn.fengzihub.p2p.business.service.ISystemAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.21.
 */
@Service
@Transactional
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService {
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;
    @Override
    public int save(SystemAccountFlow systemAccountFlow) {
        systemAccountFlowMapper.insert(systemAccountFlow);
        return 0;
    }

    @Override
    public void createSystemAccountManagementCharge(SystemAccount account, BigDecimal amount) {
        creatFlow(account, amount, BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE, "成功投标,收到借款人收取费:" + amount + "元");
    }

    @Override
    public void creatSystemAccountInterestManagerChargeFlow(SystemAccount account, BigDecimal amount) {

        creatFlow(account, amount, BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE, "收到投资人利息管理费:" + amount + "元");
    }

    @Override
    public void createGetChargeFee(SystemAccount account, BigDecimal amount) {

        creatFlow(account, amount, BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE, "收到提现手续费:" + amount + "元");
    }

    @Override
    public void createSystemAccountInterest(SystemAccount account, BigDecimal amount) {
        creatFlow(account, amount, BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_INTEREST_CHARGE, "支付体验标利息:" + amount + "元");

    }


    public void creatFlow(SystemAccount account, BigDecimal amount, int actionType, String remark) {
        SystemAccountFlow systemAccountFlow = new SystemAccountFlow();
        systemAccountFlow.setAmount(amount);
        systemAccountFlow.setRemark(remark);
        systemAccountFlow.setActionType(actionType);
        systemAccountFlow.setFreezedAmount(account.getFreezedAmount());
        systemAccountFlow.setUsableAmount(account.getUsableAmount());
        systemAccountFlow.setActionTime(new Date());
        this.save(systemAccountFlow);
    }

}
