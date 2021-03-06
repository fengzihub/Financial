package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.SystemAccount;
import cn.fengzihub.p2p.business.domain.SystemAccountFlow;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.21.
 */
public interface ISystemAccountFlowService {
    int save(SystemAccountFlow systemAccountFlow);

    /**
     * 系统账户收取管理费
     * @param account
     * @param amount
     */
    void createSystemAccountManagementCharge(SystemAccount account, BigDecimal amount);

    /**
     * 系统账户收取利息管理费
     * @param account
     * @param amount
     */
    void creatSystemAccountInterestManagerChargeFlow(SystemAccount account, BigDecimal amount);

    /**
     * 系统账户收取提现手续费
     * @param account
     * @param amount
     */
    void createGetChargeFee(SystemAccount account, BigDecimal amount);

    /**
     * 系统账户支付体验标利息
     * @param systemAccount
     * @param interest
     */
    void createSystemAccountInterest(SystemAccount account, BigDecimal amount);
}
