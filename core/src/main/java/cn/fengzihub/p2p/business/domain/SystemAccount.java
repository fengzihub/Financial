package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import cn.fengzihub.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.21.
 */
@Getter@Setter
public class SystemAccount extends BaseDomain{
    private int version;
    private BigDecimal usableAmount = BidConst.ZERO;//账户可用余额
    private BigDecimal freezedAmount = BidConst.ZERO;//账户冻结金额
}
