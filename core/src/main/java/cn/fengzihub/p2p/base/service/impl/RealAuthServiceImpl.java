package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.RealAuth;
import cn.fengzihub.p2p.base.mapper.RealAuthMapper;
import cn.fengzihub.p2p.base.service.IRealAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.12.
 */
@Service
@Transactional
public class RealAuthServiceImpl implements IRealAuthService {
    @Autowired
    private RealAuthMapper realAuthMapper;

    @Override
    public int save(RealAuth realAuth) {
        return realAuthMapper.insert(realAuth);
    }

    @Override
    public int update(RealAuth realAuth) {
        return realAuthMapper.updateByPrimaryKey(realAuth);
    }

    @Override
    public RealAuth get(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }
}
