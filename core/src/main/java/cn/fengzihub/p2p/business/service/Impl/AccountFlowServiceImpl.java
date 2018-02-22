package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.business.domain.AccountFlow;
import cn.fengzihub.p2p.business.mapper.AccountFlowMapper;
import cn.fengzihub.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.20.
 */
@Service
@Transactional
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Override
    public int save(AccountFlow accountFlow) {
        accountFlowMapper.insert(accountFlow);
        return 0;
    }

    @Override
    public void createRechargeOfflineFlow(Account account, BigDecimal amount) {

        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE,"线下充值成功" + amount + "元");
    }

    @Override
    public void createBidFlow(Account account, BigDecimal amount) {
        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_BID_FREEZED,"投标冻结:" + amount + "元");

    }

    @Override
    public void createBidFailedFlow(Account account, BigDecimal amount) {

        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,"满标一审拒绝,解冻金额:" + amount + "元");

    }

    @Override
    public void createBidRequestSuccessFlow(Account account, BigDecimal amount) {
        createFlow(account,amount,BidConst.BIDREQUEST_STATE_PAYING_BACK,"借款:" + amount + "元");
    }

    @Override
    public void createPayAccountManagementCharge(Account account, BigDecimal amount) {
        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_CHARGE,"支付平台管理费:" + amount + "元");
    }

    @Override
    public void createBidSuccessFlow(Account account, BigDecimal amount) {
        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL,"成功投资,解冻金额:" + amount + "元");
    }

    @Override
    public void createReturnMenory(Account account, BigDecimal amount) {
        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_RETURN_MONEY,"还款:" + amount + "元");
    }

    @Override
    public void createBidReturnMenoryFlow(Account account, BigDecimal amount) {

        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY,"回款:" + amount + "元");

    }

    @Override
    public void creatPayInterestManagerChargeFlow(Account account, BigDecimal amount) {

        createFlow(account,amount,BidConst.ACCOUNT_ACTIONTYPE_INTEREST_SHARE,"利息管理费Q:" + amount + "元");
    }


    private void createFlow(Account account, BigDecimal amount, int actionType, String remark) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setAmount(amount);
        flow.setTradeTime(new Date());
        flow.setUsableAmount(account.getUsableAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setActionType(actionType);
        flow.setRemark(remark);

        this.save(flow);
    }





}
