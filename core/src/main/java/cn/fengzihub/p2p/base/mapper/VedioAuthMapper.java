package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.VedioAuth;
import cn.fengzihub.p2p.base.query.VedioAuthQueryObject;

import java.util.List;

public interface VedioAuthMapper {

    int insert(VedioAuth record);

    VedioAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(VedioAuth record);


    int queryForCount(VedioAuthQueryObject qo);

    List<VedioAuth> queryForList(VedioAuthQueryObject qo);
}