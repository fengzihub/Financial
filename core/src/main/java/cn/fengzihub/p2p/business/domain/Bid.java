package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import cn.fengzihub.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.19.
 */
@Getter@Setter
public class Bid extends BaseDomain {
    private BigDecimal actualRate;//借款的利率
    private BigDecimal availableAmount;//投标的金额
    private Long       bidRequestId;//借款对象ID
    private String     bidRequestTitle;//借款对象标题
    private Logininfo  bidUser;//   投资人
    private Date       bidTime;//   投标时间
    private int        bidRequestState;//   借款的状态
}
