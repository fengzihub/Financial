package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.IpLog;
import cn.fengzihub.p2p.base.query.IplogQueryObject;

import java.util.List;

public interface IpLogMapper {

    int insert(IpLog record);

    List<IpLog> selectAll();

    List<IpLog> queryPage(IplogQueryObject qo);
}