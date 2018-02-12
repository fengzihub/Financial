package cn.fengzihub.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2018.02.12.
 */
@Getter
@Setter
public class PageResult {
    private int total;
    private List rows;

    public PageResult(int total, List rows) {
        this.total=total;
        this.rows=rows;
    }
}
