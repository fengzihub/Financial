package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import cn.fengzihub.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 体验金账户
 */
@Getter@Setter
public class ExpAccount extends BaseDomain {

    private int version;
    private BigDecimal usableAmount = BidConst.ZERO;// 体验金账户余额
    private BigDecimal freezedAmount = BidConst.ZERO;// 体验金冻结金额
    private BigDecimal unReturnExpAmount = BidConst.ZERO;// 临时垫收体验金
}
