package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.20.
 */
@Getter@Setter
public class AccountFlow extends BaseDomain{
    private Long accountId;// 流水是关于哪个账户的
    private BigDecimal amount;// 这次账户发生变化的金额
    private int actionType;// 资金变化类型
    private Date tradeTime;// 这次账户发生变化的时间
    private String remark;//账户流水说明

    private BigDecimal usableAmount;//操作之后,可用余额
    private BigDecimal freezedAmount;//操作之后,冻结金额
}
