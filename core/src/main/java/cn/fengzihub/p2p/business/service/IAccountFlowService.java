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

    /**
     * 投标的流水
     * @param account
     * @param amount
     */
    void createBidFlow(Account account, BigDecimal amount);

    /**
     * 投标失败的流水
     * @param account
     * @param amount
     */
    void createBidFailedFlow(Account account, BigDecimal amount);

    /**
     * 满标二审借款成功流水
     * @param account
     * @param amount
     */
    void createBidRequestSuccessFlow(Account account, BigDecimal amount);

    /**
     * 满标二审通过支付平台管理费
     * @param account
     * @param amount
     */
    void createPayAccountManagementCharge(Account account, BigDecimal amount);

    /**
     * 投资成功流水
     * @param acccount
     * @param amount
     */
    void createBidSuccessFlow(Account acccount, BigDecimal amount);
}
