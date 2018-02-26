package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.ExpAccount;
import cn.fengzihub.p2p.business.domain.ExpAccountFlow;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.23.
 */
public interface IExpAccountFlowService {
    int save(ExpAccountFlow expAccountFlow);

    /**
     *
     * 投资体验标流水
     * @param expAccount
     * @param amount
     */
    void createBidFlow(ExpAccount expAccount, BigDecimal amount);

    /**
     * 投标失败流水
     * @param expAccount
     * @param amount
     */
    void createBidFailedFlow(ExpAccount expAccount, BigDecimal amount);

    /**
     * 投资体验标成功流水
     * @param expAccount
     * @param availableAmount
     */
    void createBidSuccessFlow(ExpAccount expAccount, BigDecimal amount);

    /**
     * 体验标还款成功,收取体验金流水
     * @param expAccount
     * @param principal
     */
    void createRecevieFlow(ExpAccount expAccount, BigDecimal amount);
}
