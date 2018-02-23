package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.ExpAccount;
import cn.fengzihub.p2p.business.domain.ExpAccountFlow;
import cn.fengzihub.p2p.business.domain.ExpAccountGrantRecord;
import cn.fengzihub.p2p.business.mapper.ExpAccountMapper;
import cn.fengzihub.p2p.business.service.IExpAccountFlowService;
import cn.fengzihub.p2p.business.service.IExpAccountGrantRecordService;
import cn.fengzihub.p2p.business.service.IExpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.23.
 */
@Service
@Transactional
public class ExpAccountServiceImpl implements IExpAccountService {
    @Autowired
    private ExpAccountMapper expAccountMapper;
    @Autowired
    private IExpAccountGrantRecordService expAccountGrantRecordService;
    @Autowired
    private IExpAccountFlowService expAccountFlowService;
    @Override
    public int save(ExpAccount expAccount) {
        expAccountMapper.insert(expAccount);
        return 0;
    }

    @Override
    public int update(ExpAccount expAccount) {
        int count = expAccountMapper.updateByPrimaryKey(expAccount);
        if (count <= 0) {
            throw new RuntimeException("乐观锁异常expAccount:" + expAccount.getId());
        }
        return count;
    }

    @Override
    public ExpAccount get(Long id) {
        return expAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public void grantExpMoney(Long id, LastTime lastTime, BigDecimal expmoeny, int expmoneyType) {

        //创建一个发放回收记录对象
        ExpAccountGrantRecord expagr = new ExpAccountGrantRecord();
        expagr.setAmount(expmoeny);
        expagr.setGrantDate(new Date());
        expagr.setGrantType(expmoneyType);
        expagr.setGrantUserId(id);
        expagr.setNote("注册发放体验金");
        expagr.setState(ExpAccountGrantRecord.STATE_NORMAL);
        expagr.setReturnDate(lastTime.getReturnDate(new Date()));
        expAccountGrantRecordService.save(expagr);

        //修改体验金账户
        ExpAccount expAccount = expAccountMapper.selectByPrimaryKey(id);
        expAccount.setUsableAmount(expAccount.getUsableAmount().add(expmoeny));
        this.update(expAccount);

        //增加一条发放体验金流水
        ExpAccountFlow flow = new ExpAccountFlow();
        flow.setActionTime(new Date());
        flow.setActionType(expmoneyType);
        flow.setAmount(expmoeny);
        flow.setExpAccountId(id);
        flow.setFreezedAmount(expAccount.getFreezedAmount());
        flow.setNote("注册发放体验金");
        flow.setUsableAmount(expAccount.getUsableAmount());
        expAccountFlowService.save(flow);
    }

}
