package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.MoneyWithdraw;
import cn.fengzihub.p2p.business.query.MoneyWithdrawQuerObject;

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

    PageResult queryPage(MoneyWithdrawQuerObject qo);

    /**
     * 提现审核
     * @param id
     * @param state
     * @param remark
     */
    void audit(Long id, int state, String remark);

}
