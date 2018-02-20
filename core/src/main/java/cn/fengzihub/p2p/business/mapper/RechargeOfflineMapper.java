package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.RechargeOffline;
import cn.fengzihub.p2p.business.query.RechargeofflineQueryObject;

import java.util.List;

public interface RechargeOfflineMapper {

    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeOffline record);

    int queryForCount(RechargeofflineQueryObject qo);

    List<RechargeOffline> queryForList(RechargeofflineQueryObject qo);
}