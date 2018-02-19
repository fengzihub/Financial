package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.Bid;

public interface BidMapper {

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Bid record);
}