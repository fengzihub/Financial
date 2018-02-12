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

    private int page = 1; //默认
    private int rows = 5;
    public int getStart() {
        return (page - 1) * rows;
    }
}
