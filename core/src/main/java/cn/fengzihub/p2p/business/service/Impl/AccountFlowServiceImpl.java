package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.business.domain.AccountFlow;
import cn.fengzihub.p2p.business.mapper.AccountFlowMapper;
import cn.fengzihub.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.20.
 */
@Service
@Transactional
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Override
    public int save(AccountFlow accountFlow) {
        accountFlowMapper.insert(accountFlow);
        return 0;
    }

    @Override
    public void createRechargeOfflineFlow(Account account, BigDecimal amount) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setAmount(amount);
        flow.setTradeTime(new Date());
        flow.setUsableAmount(account.getUsableAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
        flow.setRemark("线下充值成功" + amount + "元");

        this.save(flow);
    }
}
