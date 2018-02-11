package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.IpLog;
import cn.fengzihub.p2p.base.query.IplogQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2018.02.10.
 */
public interface IIpLogService {
    int save(IpLog ipLog);

    PageInfo queryPage(IplogQueryObject qo);

}
