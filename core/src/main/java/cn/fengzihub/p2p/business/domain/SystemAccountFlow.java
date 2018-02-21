package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.21.
 */
@Getter
@Setter
public class SystemAccountFlow extends BaseDomain {
    private BigDecimal amount;//变化金额
    private int actionType;//流水类型
    private Date actionTime;
    private String remark;//流水信息

    private BigDecimal usableAmount;//操作之后,可用余额
    private BigDecimal freezedAmount;//操作之后,冻结金额
}
