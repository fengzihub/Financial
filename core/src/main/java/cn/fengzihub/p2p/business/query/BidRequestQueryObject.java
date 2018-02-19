package cn.fengzihub.p2p.business.query;

import cn.fengzihub.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018.02.19.
 */
@Getter
@Setter
public class BidRequestQueryObject extends QueryObject {
    private int bidRequestState = -1;
}
