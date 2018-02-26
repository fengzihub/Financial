package cn.fengzihub.p2p.business.query;

import cn.fengzihub.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018.02.22.
 */
@Getter
@Setter
public class PaymentScheduleQueryObject extends QueryObject {
    private Long userId;

    private Integer bidRequestType;

}
