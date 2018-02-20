package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.RechargeOffline;
import cn.fengzihub.p2p.business.mapper.RechargeOfflineMapper;
import cn.fengzihub.p2p.business.query.RechargeofflineQueryObject;
import cn.fengzihub.p2p.business.service.IAccountFlowService;
import cn.fengzihub.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018.02.20.
 */
@Service
@Transactional
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Override
    public int save(RechargeOffline rechargeOffline) {
        rechargeOfflineMapper.insert(rechargeOffline);
        return 0;
    }

    @Override
    public int update(RechargeOffline rechargeOffline) {
        rechargeOfflineMapper.updateByPrimaryKey(rechargeOffline);
        return 0;
    }

    @Override
    public RechargeOffline get(Long id) {
        return rechargeOfflineMapper.selectByPrimaryKey(id);
    }

    @Override
    public void apply(RechargeOffline rechargeOffline) {

        RechargeOffline offline = new RechargeOffline();
        offline.setState(RechargeOffline.STATE_NORMAL);
        offline.setApplier(UserContext.getCurrent());
        offline.setNote(rechargeOffline.getNote());
        offline.setAmount(rechargeOffline.getAmount());
        offline.setBankInfo(rechargeOffline.getBankInfo());
        offline.setTradeCode(rechargeOffline.getTradeCode());
        offline.setTradeTime(rechargeOffline.getTradeTime());
        offline.setApplyTime(new Date());
        this.save(offline);
    }

    @Override
    public PageResult queryPage(RechargeofflineQueryObject qo) {

        int count = rechargeOfflineMapper.queryForCount(qo);
        List<RechargeOffline> list = rechargeOfflineMapper.queryForList(qo);
        return new PageResult(count, list);
    }

    @Override
    public void audit(Long id, int state, String remark) {
        //根据id获取审核对象.判断处于待审核状态
        RechargeOffline offline = this.get(id);
        if (offline != null && offline.getState() == RechargeOffline.STATE_NORMAL) {
            offline.setAuditTime(new Date());
            offline.setAuditor(UserContext.getCurrent());
            offline.setRemark(remark);
            if (state == RechargeOffline.STATE_PASS) {
                offline.setState(RechargeOffline.STATE_PASS);
                Account account = accountService.get(offline.getApplier().getId());
                account.setUsableAmount(account.getUsableAmount().add(offline.getAmount()));
                accountService.update(account);
                accountFlowService.createRechargeOfflineFlow(account,offline.getAmount());
            } else {
                offline.setState(RechargeOffline.STATE_REJECT);
            }
            this.update(offline);
        }
    }
}
