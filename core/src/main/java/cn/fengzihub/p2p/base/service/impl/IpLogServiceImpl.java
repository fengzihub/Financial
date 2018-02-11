package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.IpLog;
import cn.fengzihub.p2p.base.mapper.IpLogMapper;
import cn.fengzihub.p2p.base.query.IplogQueryObject;
import cn.fengzihub.p2p.base.service.IIpLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018.02.10.
 */
@Service@Transactional
public class IpLogServiceImpl implements IIpLogService {
    @Autowired
    private IpLogMapper ipLogMapper;
    @Override
    public int save(IpLog ipLog) {
        ipLogMapper.insert(ipLog);
        return 0;
    }

    @Override
    public PageInfo queryPage(IplogQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<IpLog> list = ipLogMapper.queryPage(qo);
        return new PageInfo(list);
    }
}
