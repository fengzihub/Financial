package cn.fengzihub.p2p.base.query;

import cn.fengzihub.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by Administrator on 2018.02.10.
 */
@Getter
@Setter
public class IplogQueryObject extends QueryObject {
    private Date beginDate;
    private Date endDate;
    private String username;
    private int state;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }


    public String getUsername() {
        return StringUtils.hasLength(username) ? username : null;
    }
}
