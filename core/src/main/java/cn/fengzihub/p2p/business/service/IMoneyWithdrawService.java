package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.MoneyWithdraw;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.22.
 */
public interface IMoneyWithdrawService {
    int save(MoneyWithdraw moneyWithdraw);

    int update(MoneyWithdraw moneyWithdraw);

    MoneyWithdraw get(Long id);

    /**
     * 提现申请
     * @param moneyAmount
     */
    void applay(BigDecimal moneyAmount);
}
