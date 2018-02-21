package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.Bid;

/**
 * Created by Administrator on 2018.02.19.
 */
public interface IBidService {
    int save(Bid bid);

    int update(Bid bid);

    Bid get(Long id);

    /**
     * 批量修改投标的状态
     * @param bidRequestId
     * @param bidRequestState
     */
    void updateState(Long bidRequestId, int bidRequestState);
}
