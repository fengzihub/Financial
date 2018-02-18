package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.Logininfo;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.domain.VedioAuth;
import cn.fengzihub.p2p.base.mapper.VedioAuthMapper;
import cn.fengzihub.p2p.base.query.VedioAuthQueryObject;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.service.IVedioAuthService;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018.02.18.
 */

@Service
@Transactional
public class VedioAuthServiceImpl implements IVedioAuthService {
    @Autowired
    private VedioAuthMapper vedioAuthMapper;
    @Autowired
    private IUserinfoService userinfoService;
    @Override
    public int save(VedioAuth vedioAuth) {
        vedioAuthMapper.insert(vedioAuth);
        return 0;
    }

    @Override
    public int update(VedioAuth vedioAuth) {
        vedioAuthMapper.updateByPrimaryKey(vedioAuth);
        return 0;
    }

    @Override
    public VedioAuth get(Long id) {
        return vedioAuthMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult queryPage(VedioAuthQueryObject qo) {
        int count = vedioAuthMapper.queryForCount(qo);
        List<VedioAuth> list = vedioAuthMapper.queryForList(qo);

        return new PageResult(count, list);
    }

    @Override
    public void audit(Long id, int state, String remark) {

        Userinfo userinfo = userinfoService.get(id);
        if (userinfo != null && !userinfo.getIsVedioAuth()) {
            VedioAuth va = new VedioAuth();
            Logininfo applier = new Logininfo();
            applier.setId(id);

            va.setApplier(applier);
            va.setApplyTime(new Date());
            va.setAuditor(UserContext.getCurrent());
            va.setAuditTime(new Date());
            va.setRemark(remark);
            if (state == VedioAuth.STATE_PASS) {
                va.setState(VedioAuth.STATE_PASS);
                userinfo.addSate(BitStatesUtils.OP_VEDIO_AUTH);
                userinfoService.update(userinfo);
            } else {
                va.setState(VedioAuth.STATE_REJECT);
            }
            this.save(va);
        }

    }
}
