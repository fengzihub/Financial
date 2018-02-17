package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.RealAuth;
import cn.fengzihub.p2p.base.query.RealAuthQuerObject;

import java.util.List;

public interface RealAuthMapper {

    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    int queryForCount(RealAuthQuerObject qo);

    List<RealAuth> queryForList(RealAuthQuerObject qo);
}