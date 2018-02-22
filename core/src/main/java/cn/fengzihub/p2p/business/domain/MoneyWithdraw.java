package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseAuthDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 提现金额
 */
@Getter
@Setter
public class MoneyWithdraw extends BaseAuthDomain {

    private BigDecimal amount;
    private BigDecimal chargeFee;//手续费

    private String bankName;
    private String accountName;
    private String accountNumber;
    private String forkName;


}
