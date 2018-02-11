package cn.fengzihub.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018.02.10.
 */
@Getter
@Setter
public class QueryObject {
    private Integer currentPage=1;//当前页
    private Integer pageSize=10;
}
