package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.business.domain.ExpAccount;
import cn.fengzihub.p2p.business.domain.ExpAccountFlow;
import cn.fengzihub.p2p.business.mapper.ExpAccountFlowMapper;
import cn.fengzihub.p2p.business.service.IExpAccountFlowService;
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
public class ExpAccountFlowServiceImpl implements IExpAccountFlowService {
    @Autowired
    private ExpAccountFlowMapper expAccountFlowMapper;
    @Override
    public int save(ExpAccountFlow expAccountFlow) {
        expAccountFlowMapper.insert(expAccountFlow);
        return 0;
    }

    @Override
    public void createBidFlow(ExpAccount expAccount, BigDecimal amount) {
        createFlow(expAccount, amount, BidConst.EXPMONEY_FLOW_BID, "体验金投标" + amount + "元");
    }

    @Override
    public void createBidFailedFlow(ExpAccount expAccount, BigDecimal amount) {
        createFlow(expAccount, amount, BidConst.EXPMONEY_FLOW_BID_FAILED, "投体验标失败,解冻:" + amount + "元");
    }

    @Override
    public void createBidSuccessFlow(ExpAccount expAccount, BigDecimal amount) {
        createFlow(expAccount, amount, BidConst.EXPMONEY_FLOW_BID_SUCCESS, "投体验标成功:" + amount + "元");

    }

    @Override
    public void createRecevieFlow(ExpAccount expAccount, BigDecimal amount) {
        createFlow(expAccount, amount, BidConst.EXPMONEY_FLOW_RETURN_SUCCESS, "体验标结算成功,收到体验本金:" + amount + "元");

    }


    private void createFlow(ExpAccount expAccount, BigDecimal amount, int actionType, String remark) {
        ExpAccountFlow flow = new ExpAccountFlow();
        flow.setExpAccountId(expAccount.getId());
        flow.setAmount(amount);
        flow.setActionTime(new Date());
        flow.setUsableAmount(expAccount.getUsableAmount());
        flow.setFreezedAmount(expAccount.getFreezedAmount());
        flow.setActionType(actionType);
        flow.setNote(remark);

        this.save(flow);
    }

}
