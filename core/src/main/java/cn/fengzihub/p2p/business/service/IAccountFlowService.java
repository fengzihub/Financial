package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.business.domain.AccountFlow;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.20.
 */
public interface IAccountFlowService {
    int save(AccountFlow accountFlow);

    /**
     * 充值成功的流水
     * @param account
     * @param amount
     */
    void createRechargeOfflineFlow(Account account, BigDecimal amount);
}
