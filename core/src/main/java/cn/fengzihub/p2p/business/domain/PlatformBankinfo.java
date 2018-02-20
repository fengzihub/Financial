package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018.02.20.
 */
@Getter@Setter
public class PlatformBankinfo extends BaseDomain {
    private String bankName;    //银行名称
    private String accountNumber;  //银行账号
    private String bankForkName;    //支行名称
    private String accountName; //开户人姓名
}
