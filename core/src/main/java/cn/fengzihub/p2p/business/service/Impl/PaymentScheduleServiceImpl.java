package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.*;
import cn.fengzihub.p2p.business.mapper.PaymentScheduleMapper;
import cn.fengzihub.p2p.business.query.PaymentScheduleQueryObject;
import cn.fengzihub.p2p.business.service.*;
import cn.fengzihub.p2p.business.util.CalculatetUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018.02.21.
 */
@Service
@Transactional
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private ISystemAccountFlowService systemAccountFlowService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IBidService bidService;
    @Autowired
    private IExpAccountService expAccountService;
    @Autowired
    private IExpAccountFlowService expAccountFlowService;

    @Override
    public int save(PaymentSchedule paymentSchedule) {
        paymentScheduleMapper.insert(paymentSchedule);
        return 0;
    }

    @Override
    public int update(PaymentSchedule paymentSchedule) {
        paymentScheduleMapper.updateByPrimaryKey(paymentSchedule);
        return 0;
    }

    @Override
    public PaymentSchedule get(Long id) {
        return paymentScheduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo queryPage(PaymentScheduleQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List list = paymentScheduleMapper.queryPage(qo);
        return new PageInfo(list);
    }

    @Override
    public void returnMoney(Long id) {

        //还款条件限制
        //获取还款对象
        PaymentSchedule paymentSchedule = this.get(id);
        Account payaccount = accountService.getCurrent();
        SystemAccount systemAccount = systemAccountService.getCurrent();
        //判断条件是否满足
        if (paymentSchedule != null && paymentSchedule.getState() == BidConst.PAYMENT_STATE_NORMAL &&  //判断当前登录人是还款人
                ((paymentSchedule.getBidRequestType() == BidConst.BIDREQUEST_TYPE_NORMAL &&
                        UserContext.getCurrent().getId().equals(paymentSchedule.getBorrowUser().getId()) &&//获取还款人账户
                        payaccount.getUsableAmount().compareTo(paymentSchedule.getTotalAmount()) >= 0)//判断还款人账户可用金额大于本期需还款金额
                        ||
                        (paymentSchedule.getBidRequestType() == BidConst.BIDREQUEST_TYPE_EXP &&
                                systemAccount.getUsableAmount().compareTo(paymentSchedule.getInterest()) >= 0
                        ))) {

            if (paymentSchedule.getBidRequestType() == BidConst.BIDREQUEST_TYPE_NORMAL) {
                //还款明细对象操作
                //统一更新还款明细对象中还款时间
                paymentScheduleDetailService.updatePayDate(paymentSchedule.getId(), paymentSchedule.getPayDate());
                //还款人:
                //可用金额减少
                payaccount.setUsableAmount(payaccount.getUsableAmount().subtract(paymentSchedule.getTotalAmount()));
                //待还金额减少
                payaccount.setUnReturnAmount(payaccount.getUnReturnAmount().subtract(paymentSchedule.getTotalAmount()));
                //账户剩余授信额度增加
                payaccount.setRemainBorrowLimit(payaccount.getRemainBorrowLimit().add(paymentSchedule.getPrincipal()));
                //生成还款成功流水
                accountFlowService.createReturnMenory(payaccount, paymentSchedule.getTotalAmount());
                accountService.update(payaccount);
            }
            //还款对象
            paymentSchedule.setState(BidConst.PAYMENT_STATE_DONE);//已还款
            paymentSchedule.setPayDate(new Date());
            this.update(paymentSchedule);

            //投资人:
            // 遍历还款明细
            Long bidId;
            Account bidUseraccount;
            ExpAccount expAccount;
            Map<Long, Account> accountMap = new HashMap<Long, Account>();
            Map<Long, ExpAccount> expAccountMap = new HashMap<Long, ExpAccount>();
            for (PaymentScheduleDetail psd : paymentSchedule.getDetails()) {
                bidId = psd.getInvestorId();
                bidUseraccount = accountMap.get(bidId);
                expAccount = expAccountMap.get(bidId);
                BigDecimal interestManagerCharge;
                if (bidUseraccount == null) {
                    bidUseraccount = accountService.get(bidId);
                    accountMap.put(bidId, bidUseraccount);
                    expAccount = expAccountService.get(bidId);
                    expAccountMap.put(bidId, expAccount);
                }
                if (paymentSchedule.getBidRequestType() == BidConst.BIDREQUEST_TYPE_NORMAL) {
                    //投资人的可用余额增加
                    bidUseraccount.setUsableAmount(bidUseraccount.getUsableAmount().add(psd.getTotalAmount()));
                    //生成回款流水
                    accountFlowService.createBidReturnMenoryFlow(bidUseraccount, psd.getTotalAmount());
                    bidUseraccount.setUnReceivePrincipal(bidUseraccount.getUnReceivePrincipal().subtract(psd.getPrincipal()));
                } else {
                    expAccount.setUsableAmount(expAccount.getUsableAmount().add(psd.getPrincipal()));
                    expAccountFlowService.createRecevieFlow(expAccount, psd.getPrincipal());
                    //系统账户支付利息
                    systemAccount.setUsableAmount(systemAccount.getUsableAmount().subtract(psd.getInterest()));
                    systemAccountFlowService.createSystemAccountInterest(systemAccount, psd.getInterest());

                    //投资人收取利息
                    bidUseraccount.setUsableAmount(bidUseraccount.getUsableAmount().add(psd.getInterest()));
                    accountFlowService.createRecevice(bidUseraccount, psd.getInterest());
                }
                //投资人待收本金和本金利息减少
                bidUseraccount.setUnReceiveInterest(bidUseraccount.getUnReceiveInterest().subtract(psd.getInterest()));

                //支付平台利息管理费
                interestManagerCharge = CalculatetUtil.calInterestManagerCharge(psd.getInterest());
                systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interestManagerCharge));
                //系统收到利息管理费流水
                systemAccountFlowService.creatSystemAccountInterestManagerChargeFlow(systemAccount, interestManagerCharge);
                //交了利息管理费,投资人可用金额减少
                bidUseraccount.setUsableAmount(bidUseraccount.getUsableAmount().subtract(interestManagerCharge));

                //生成支付利息管理费流水
                accountFlowService.creatPayInterestManagerChargeFlow(bidUseraccount, interestManagerCharge);

            }
            for (Account account : accountMap.values()) {
                accountService.update(account);
            }
            for (ExpAccount expaccount : expAccountMap.values()) {
                expAccountService.update(expaccount);
            }
            systemAccountService.update(systemAccount);


            // 如果是最后一个还款 , 修改借款状态,修改投标状态
            Long bidRequestId = paymentSchedule.getBidRequestId();
            List<PaymentSchedule> paymentScheduleList = paymentScheduleMapper.selectBidRequestId(bidRequestId);
            for (PaymentSchedule schedule : paymentScheduleList) {
                if (schedule.getState() != BidConst.PAYMENT_STATE_DONE) {
                    return;
                }
            }
            //获取借款对象,更改借款状态
            BidRequest bidRequest = bidRequestService.get(paymentSchedule.getBidRequestId());
            bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            bidRequestService.update(bidRequest);

            bidService.updateState(paymentSchedule.getBidRequestId(), BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
        }
    }

    @Override
    public PageResult queryPageResult(PaymentScheduleQueryObject qo) {
        int count = paymentScheduleMapper.queryForCount(qo);
        List<PaymentSchedule> list = paymentScheduleMapper.queryForList(qo);
        return new PageResult(count, list);
    }


}

