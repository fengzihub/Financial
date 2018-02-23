package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.ExpAccountFlow;
import cn.fengzihub.p2p.business.mapper.ExpAccountFlowMapper;
import cn.fengzihub.p2p.business.service.IExpAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.23.
 */
@Service
@Transactional
public class ExpAccountFlowServiceImpl implements IExpAccountFlowService {
    @Autowired
    private ExpAccountFlowMapper expAccountFlowMapper;
    @Override
    public int save(ExpAccountFlow expAccountFlow) {
        expAccountFlowMapper.insert(expAccountFlow);
        return 0;
    }
}
