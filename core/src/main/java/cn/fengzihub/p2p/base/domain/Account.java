package cn.fengzihub.p2p.base.domain;

import cn.fengzihub.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.10.
 */
@Getter
@Setter
public class Account extends BaseDomain {
    private int version;//乐观锁
    private String tradePassword;//交易密码
    private BigDecimal usableAmount= BidConst.ZERO;//账户可用余额
    private BigDecimal freezedAmount= BidConst.ZERO;//账户冻结金额
    private BigDecimal unReceiveInterest= BidConst.ZERO;//账户待收利息
    private BigDecimal unReceivePrincipal= BidConst.ZERO;//账户待收本金
    private BigDecimal unReturnAmount= BidConst.ZERO;//账户待还金额
    private BigDecimal remainBorrowLimit= BidConst.INIT_BORROW_LIMIT;//账户剩余授信额度
    private BigDecimal borrowLimit= BidConst.INIT_BORROW_LIMIT;//账户授信额度

    public BigDecimal getTotalAmount() {
        return usableAmount.add(freezedAmount).add(unReceiveInterest).add(unReceivePrincipal);
    }

}
