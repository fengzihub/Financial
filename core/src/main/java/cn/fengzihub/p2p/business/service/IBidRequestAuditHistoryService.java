package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.BidRequestAuditHistory;

import java.util.List;

/**
 * Created by Administrator on 2018.02.19.
 */
public interface IBidRequestAuditHistoryService {
    int save(BidRequestAuditHistory bidRequestAuditHistory);

    List<BidRequestAuditHistory> queryByBidRequestId(Long id);
}
