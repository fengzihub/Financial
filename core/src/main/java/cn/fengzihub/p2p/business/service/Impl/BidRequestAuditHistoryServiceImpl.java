package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.BidRequestAuditHistory;
import cn.fengzihub.p2p.business.mapper.BidRequestAuditHistoryMapper;
import cn.fengzihub.p2p.business.service.IBidRequestAuditHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018.02.19.
 */
@Service
@Transactional
public class BidRequestAuditHistoryServiceImpl implements IBidRequestAuditHistoryService {
    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;
    @Override
    public int save(BidRequestAuditHistory bidRequestAuditHistory) {
        bidRequestAuditHistoryMapper.insert(bidRequestAuditHistory);
        return 0;
    }

    @Override
    public List<BidRequestAuditHistory> queryByBidRequestId(Long bidRequestId) {
        return bidRequestAuditHistoryMapper.queryByBidRequestId(bidRequestId);
    }
}
