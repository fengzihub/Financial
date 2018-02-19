package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.Bid;
import cn.fengzihub.p2p.business.mapper.BidMapper;
import cn.fengzihub.p2p.business.service.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.19.
 */
@Service
@Transactional
public class BidServiceImpl implements IBidService {
    @Autowired
    private BidMapper bidMapper;
    @Override
    public int save(Bid bid) {
        bidMapper.insert(bid);
        return 0;
    }

    @Override
    public int update(Bid bid) {
        bidMapper.updateByPrimaryKey(bid);
        return 0;
    }

    @Override
    public Bid get(Long id) {
        return bidMapper.selectByPrimaryKey(id);
    }
}
