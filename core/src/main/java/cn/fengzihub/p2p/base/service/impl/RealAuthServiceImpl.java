package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.RealAuth;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.mapper.RealAuthMapper;
import cn.fengzihub.p2p.base.query.RealAuthQuerObject;
import cn.fengzihub.p2p.base.service.IRealAuthService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018.02.12.
 */
@Service
@Transactional
public class RealAuthServiceImpl implements IRealAuthService {
    @Autowired
    private RealAuthMapper realAuthMapper;
    @Autowired
    private IUserinfoService userinfoService;
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

    @Override
    public void realAuthSave(RealAuth realAuth) {
        //实名认证信息保存到数据库中
        RealAuth re = new RealAuth();
        re.setAddress(realAuth.getAddress());
        re.setApplier(UserContext.getCurrent());
        re.setApplyTime(new Date());
        re.setBornDate(realAuth.getBornDate());
        re.setIdNumber(realAuth.getIdNumber());
        re.setImage1(realAuth.getImage1());
        re.setImage2(realAuth.getImage2());
        re.setRealName(realAuth.getRealName());
        re.setSex(realAuth.getSex());
        re.setState(RealAuth.STATE_NORMAL);
        this.save(re);

        Userinfo current = userinfoService.getCurrent();
        current.setRealAuthId(re.getId());
        userinfoService.update(current);
    }

    @Override
    public PageResult queryPage(RealAuthQuerObject qo) {
        int total = realAuthMapper.queryForCount(qo);
       /* if (total == 0) {
            return new PageResult(total, Collections.EMPTY_LIST);
        }*/
        List<RealAuth> realAuthLists = realAuthMapper.queryForList(qo);
        return new PageResult(total,realAuthLists);
    }

    @Override
    public void auth(Long id, int state, String remark) {
        //根据id查询实名认证审核对象,判断是否为null,判断是否已经审核
        RealAuth realAuth = this.get(id);
        if (realAuth != null && realAuth.getState() == RealAuth.STATE_NORMAL) {
            //设置审核人,审核时候,审核备注
            realAuth.setAuditor(UserContext.getCurrent());
            realAuth.setAuditTime(new Date());
            realAuth.setRemark(remark);
            Userinfo applier = userinfoService.get(realAuth.getApplier().getId());

            if (state == RealAuth.STATE_PASS) {
                realAuth.setState(RealAuth.STATE_PASS);
                applier.addSate(BitStatesUtils.OP_REAL_AUTH);
                applier.setRealName(realAuth.getRealName());
                applier.setIdNumber(realAuth.getIdNumber());
            } else {
                //审核拒绝
                realAuth.setState(RealAuth.STATE_REJECT);

                //状态修改审核拒绝
                //userinfo 中的realAuth设置为null
                applier.setRealAuthId(null);
            }
            this.update(realAuth);
            userinfoService.update(applier);
        }
    }
}
