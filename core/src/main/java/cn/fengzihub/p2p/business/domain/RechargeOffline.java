package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseAuthDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.20.
 */
@Getter@Setter
public class RechargeOffline extends BaseAuthDomain{
    private PlatformBankinfo bankInfo;//
    private String tradeCode;//交易号
    private BigDecimal amount;//充值金额
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date tradeTime;//充值时间
    private String note;//充值说明
}
