package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.PlatformBankinfo;
import cn.fengzihub.p2p.business.mapper.PlatformBankinfoMapper;
import cn.fengzihub.p2p.business.query.PlatformDescriptionQueryObject;
import cn.fengzihub.p2p.business.service.IPlatformBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018.02.20.
 */
@Service
@Transactional
public class PlatformBankinfoServiceImpl implements IPlatformBankinfoService {
    @Autowired
    private PlatformBankinfoMapper platformBankinfoMapper;
    @Override
    public int save(PlatformBankinfo platformBankinfo) {
        platformBankinfoMapper.insert(platformBankinfo);
        return 0;
    }

    @Override
    public int update(PlatformBankinfo platformBankinfo) {
        platformBankinfoMapper.updateByPrimaryKey(platformBankinfo);
        return 0;
    }

    @Override
    public PlatformBankinfo get(Long id) {
        return platformBankinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult queryPage(PlatformDescriptionQueryObject qo) {
        int count = platformBankinfoMapper.queryForCount(qo);
        List<PlatformBankinfo> list = platformBankinfoMapper.queryForList(qo);

        return new PageResult(count, list);
    }

    @Override
    public List<PlatformBankinfo> selectAll() {
        return platformBankinfoMapper.selectAll();
    }
}
