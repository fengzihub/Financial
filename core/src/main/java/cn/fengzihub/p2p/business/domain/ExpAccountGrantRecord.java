package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体验金发放和回收记录
 */
@Getter@Setter
public class ExpAccountGrantRecord extends BaseDomain {
    public static final int STATE_NORMAL = 0;// 正常
    public static final int STATE_RETURN = 1;// 已回收

    private Long grantUserId;// 对应用户
    private BigDecimal amount;// 发放金额
    private Date grantDate;// 发放时间
    private Date returnDate;// 到期时间
    private int grantType;// 发放类型
    private String note;// 说明
    private int state;// 当前状态
}
