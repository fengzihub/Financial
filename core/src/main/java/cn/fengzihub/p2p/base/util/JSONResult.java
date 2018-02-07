package cn.fengzihub.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JSONResult {
    private boolean success = true;
    private String msg;


    //标记为错误状态,设置错误信息
    public void mark(String errorMsg) {
        this.success = false;
        this.msg = errorMsg;
    }
}
