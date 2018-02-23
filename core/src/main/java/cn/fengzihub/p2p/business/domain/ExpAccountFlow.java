package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体验金流水
 */
@Getter@Setter
public class ExpAccountFlow extends BaseDomain {
    private Long expAccountId;//体验金账户
    private Date actionTime;// 交易时间
    private BigDecimal amount;// 交易金额
    private int actionType;// 交易类型
    private String note;// 说明
    private BigDecimal usableAmount;// 交易之后的体验金账户余额
    private BigDecimal freezedAmount;// 交易之后的体验金账户冻结金额
}
