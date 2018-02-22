package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.*;
import cn.fengzihub.p2p.business.mapper.BidRequestMapper;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;
import cn.fengzihub.p2p.business.service.*;
import cn.fengzihub.p2p.business.util.CalculatetUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Administrator on 2018.02.19.
 */
@Service@Transactional
public class BidRequestServiceImpl implements IBidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IBidRequestAuditHistoryService bidRequestAuditHistoryService;
    @Autowired
    private IBidService bidService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private ISystemAccountFlowService systemAccountFlowService;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;
    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;
    @Override
    public int save(BidRequest bidRequest) {
        bidRequestMapper.insert(bidRequest);
        return 0;
    }

    @Override
    public int update(BidRequest bidRequest) {
        int count = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (count <= 0) {
            throw new RuntimeException("乐观锁异常,bidRequestId:" + bidRequest.getId());
        }
        return 0;
    }

    @Override
    public BidRequest get(Long id) {

        return bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean canApplyBorrow(Userinfo userinfo) {
        //判断用户是否填写基本资料,视频认证,实名认证
        if (userinfo.getIsBasicInfo()&&
                userinfo.getIsVedioAuth()&&
                userinfo.getIsRealAuth()&&
                userinfo.getScore() >= BidConst.CREDIT_BORROW_SCORE) {
            return true;
        }
        return false;
    }

    @Override
    public void apply(BidRequest bidRequest) {

        Userinfo userinfo = userinfoService.getCurrent();
        Account account = accountService.getCurrent();
        if (this.canApplyBorrow(userinfo) &&
                !userinfo.gethasBidRequestProcess() &&
                bidRequest.getBidRequestAmount().compareTo(BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0 &&
                bidRequest.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0 &&
                bidRequest.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0 &&
                bidRequest.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0 &&
                bidRequest.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT)>= 0
                ) {

            BidRequest br = new BidRequest();
            br.setApplyTime(new Date());
            br.setBidRequestAmount(bidRequest.getBidRequestAmount());
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
            br.setBidRequestType(BidConst.BIDREQUEST_TYPE_NORMAL);
            br.setCreateUser(UserContext.getCurrent());
            br.setCurrentRate(bidRequest.getCurrentRate());
            br.setDescription(bidRequest.getDescription());
            br.setDisableDate(bidRequest.getDisableDate());
            br.setMinBidAmount(bidRequest.getMinBidAmount());
            br.setMonthes2Return(bidRequest.getMonthes2Return());
            br.setReturnType(BidConst.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL);
            br.setTitle(bidRequest.getTitle());
            BigDecimal bigDecimal = CalculatetUtil.calTotalInterest(br.getReturnType(), br.getBidRequestAmount(), br.getCurrentRate(), br.getMonthes2Return());
            br.setTotalRewardAmount(bigDecimal);
            this.save(br);

            userinfo.addSate(BitStatesUtils.HAS_BIDREQUEST_PROCESS);
            userinfoService.update(userinfo);
        }

    }

    @Override
    public PageResult queryPage(BidRequestQueryObject qo) {
        int count = bidRequestMapper.queryForCount(qo);
        List<BidRequest> list = bidRequestMapper.queryForList(qo);
        return new PageResult(count, list);
    }

    @Override
    public void publishAudit(Long id, int state, String remark) {

        //发标前审核
        //根据id查询BidRequest对象,判断借款对象是否处于待审核状态
        BidRequest bidRequest = this.get(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING) {
            //创建审核历史对象,
            BidRequestAuditHistory brh = new BidRequestAuditHistory();
            brh.setApplier(bidRequest.getCreateUser());
            brh.setApplyTime(bidRequest.getApplyTime());
            brh.setAuditTime(new Date());
            brh.setAuditor(UserContext.getCurrent());
            brh.setRemark(remark);
            brh.setBidRequestId(bidRequest.getId());
            brh.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);
            if (state == BidRequestAuditHistory.STATE_PASS) {
                brh.setState(BidRequestAuditHistory.STATE_PASS);
                //审核通过
                //设置标的状态,招标截止时间,标的发布时间,标的风控意见
                bidRequest.setPublishTime(new Date());
                bidRequest.setNote(remark);
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
                bidRequest.setDisableDate(DateUtils.addDays(bidRequest.getPublishTime(), bidRequest.getDisableDays()));
            } else {
                //审核拒绝
                brh.setState(BidRequestAuditHistory.STATE_REJECT);
                //设置标的状态
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                //找到申请人,移除借款状态码
                Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
                userinfo.removeSate(BitStatesUtils.HAS_BIDREQUEST_PROCESS);
                userinfoService.update(userinfo);
            }
            bidRequestAuditHistoryService.save(brh);
            this.update(bidRequest);
        }
    }

    @Override
    public PageInfo pageInfoPage(BidRequestQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List list = bidRequestMapper.pageInfoPage(qo);
        return new PageInfo(list);
    }

    @Override
    public void bid(Long bidRequestId, BigDecimal amount) {

        BidRequest bidRequest = this.get(bidRequestId);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_BIDDING) {
            //判断当前登录人不是借款人
            if (!UserContext.getCurrent().getId().equals(bidRequest.getCreateUser().getId())) {
                Account account = accountService.getCurrent();
                if (amount.compareTo(bidRequest.getMinBidAmount())>= 0 &&
                        amount.compareTo(account.getUsableAmount().min(bidRequest.getRemainAmount()))<=0
                        ) {

                    bidRequest.setBidCount(bidRequest.getBidCount()+1);
                    bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));

                    //投资对象
                    Bid bid = new Bid();
                    bid.setActualRate(bidRequest.getCurrentRate());
                    bid.setAvailableAmount(amount);
                    bid.setBidRequestId(bidRequest.getId());
                    bid.setBidRequestTitle(bidRequest.getTitle());
                    bid.setBidTime(new Date());
                    bid.setBidUser(UserContext.getCurrent());
                    bid.setBidRequestState(bidRequest.getBidRequestState());
                    bidService.save(bid);

                    //投资人账户
                    //可用金额,冻结金额
                    account.setUsableAmount(account.getUsableAmount().subtract(amount));
                    account.setFreezedAmount(account.getFreezedAmount().add(amount));
                    accountService.update(account);
                    //生成投标流水
                    accountFlowService.createBidFlow(account,amount);
                    //判断满标
                    if (bidRequest.getCurrentSum().compareTo(bidRequest.getBidRequestAmount()) == 0) {
                        bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
                        bidService.updateState(bidRequest.getId(),BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
                    }
                    this.update(bidRequest);
                }
            }
        }
    }

    //满标一审
    @Override
    public void audit1(Long id, int state, String remark) {
        BidRequest bidRequest = this.get(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1) {
            //审核记录
            BidRequestAuditHistory brah = new BidRequestAuditHistory();
            brah.setBidRequestId(bidRequest.getId());
            brah.setRemark(remark);
            brah.setAuditor(UserContext.getCurrent());
            brah.setApplier(bidRequest.getCreateUser());
            brah.setApplyTime(new Date());
            brah.setAuditType(BidRequestAuditHistory.FULL_AUDIT_1);
            brah.setAuditTime(new Date());
            bidRequestAuditHistoryService.save(brah);
            //审核通过
            if (state == BidRequestAuditHistory.STATE_PASS) {
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
                bidService.updateState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
            } else {
                //审核拒绝
                auditReject(bidRequest);
            }
            this.update(bidRequest);
        }
    }


    //提取出审核拒绝方法
    private void auditReject(BidRequest bidRequest) {
        //修改借款对象状态
        bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
        //修改投标的状态
        bidService.updateState(bidRequest.getId(),BidConst.BIDREQUEST_STATE_REJECTED);

        //修改投标对象
        Long bidUserId;
        Account bidUseraccount;
        Map<Long, Account> accountMap = new HashMap<Long, Account>();
        for (Bid bid : bidRequest.getBids()) {
            bidUserId = bid.getBidUser().getId();
            bidUseraccount = accountMap.get(bidUserId);
            if (bidUseraccount == null) {
                bidUseraccount = accountService.get(bidUserId);
                accountMap.put(bidUserId, bidUseraccount);
            }
            bidUseraccount.setFreezedAmount(bidUseraccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
            bidUseraccount.setUsableAmount(bidUseraccount.getUsableAmount().add(bid.getAvailableAmount()));

            //生成投标失败的流水
            accountFlowService.createBidFailedFlow(bidUseraccount,bid.getAvailableAmount());
        }
        for (Account account : accountMap.values()) {
            accountService.update(account);
        }
        //借款人userinfo对象去掉正在借款的状态码,更新借款人userinfo对象
        Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
        userinfo.removeSate(BitStatesUtils.HAS_BIDREQUEST_PROCESS);
        userinfoService.update(userinfo);
    }


    //满标二审
    @Override
    public void audit2(Long id, int state, String remark) {
        BidRequest bidRequest = this.get(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2) {
            //审核历史记录
            BidRequestAuditHistory brah = new BidRequestAuditHistory();
            brah.setBidRequestId(bidRequest.getId());
            brah.setRemark(remark);
            brah.setAuditor(UserContext.getCurrent());
            brah.setApplier(bidRequest.getCreateUser());
            brah.setApplyTime(new Date());
            brah.setAuditType(BidRequestAuditHistory.FULL_AUDIT_2);
            brah.setAuditTime(new Date());
            bidRequestAuditHistoryService.save(brah);

            //审核通过
            if (state == BidRequestAuditHistory.STATE_PASS) {
                //设置借款对象的状态
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);
                //设置投标对象的状态
                bidService.updateState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_PAYING_BACK);
                //借款人可用金额增加
                Account bidRequestaccount = accountService.get(bidRequest.getCreateUser().getId());
                bidRequestaccount.setUsableAmount(bidRequestaccount.getUsableAmount().add(bidRequest.getBidRequestAmount()));
                //借款人剩余授信额度减少
                bidRequestaccount.setRemainBorrowLimit(bidRequestaccount.getRemainBorrowLimit().subtract(bidRequest.getBidRequestAmount()));
                //借款人账户中待还金额增加
                BigDecimal b1 = bidRequest.getBidRequestAmount().add(bidRequest.getTotalRewardAmount());
                bidRequestaccount.setUnReturnAmount(bidRequestaccount.getUnReturnAmount().add(b1));
                //生成借款人的流水对象
                accountFlowService.createBidRequestSuccessFlow(bidRequestaccount, bidRequest.getBidRequestAmount());

                //移除申请人的借款状态码
                Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
                userinfo.removeSate(BitStatesUtils.HAS_BIDREQUEST_PROCESS);
                userinfoService.update(userinfo);
                //支付平台管理费
                BigDecimal accountManagementCharge = CalculatetUtil.calAccountManagementCharge(bidRequest.getBidRequestAmount());
                bidRequestaccount.setUsableAmount(bidRequestaccount.getUsableAmount().subtract(accountManagementCharge));
                accountFlowService.createPayAccountManagementCharge(bidRequestaccount, accountManagementCharge);
                accountService.update(bidRequestaccount);
                //支付流水
                SystemAccount systemAccount = systemAccountService.getCurrent();
                systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(accountManagementCharge));
                systemAccountService.update(systemAccount);

                //增加系统账户收取平台管理费的流水
                systemAccountFlowService.createSystemAccountManagementCharge(systemAccount, accountManagementCharge);

                //更新投资人账号
                Long bidUserId;
                Account bidUseracccount;
                Map<Long, Account> accountMap = new HashMap<Long, Account>();
                for (Bid bid : bidRequest.getBids()) {

                    bidUserId = bid.getBidUser().getId();
                    bidUseracccount = accountMap.get(bidUserId);
                    if (bidUseracccount == null) {
                        bidUseracccount = accountService.get(bidUserId);
                        accountMap.put(bidUserId, bidUseracccount);
                    }
                    //投资人冻结金额减少
                    bidUseracccount.setFreezedAmount(bidUseracccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
                    accountFlowService.createBidSuccessFlow(bidUseracccount, bid.getAvailableAmount());
                }

                //还款对象.....
                List<PaymentSchedule> paymentSchedules = createPaymentSchedule(bidRequest);
                //计算投资人的待收利息和待收本金
                for (PaymentSchedule ps : paymentSchedules) {
                    for (PaymentScheduleDetail psd : ps.getDetails()) {
                        bidUseracccount = accountMap.get(psd.getInvestorId());
                        bidUseracccount.setUnReceiveInterest(bidUseracccount.getUnReceiveInterest().add(psd.getInterest()));
                        bidUseracccount.setUnReceivePrincipal(bidUseracccount.getUnReceivePrincipal().add(psd.getPrincipal()));
                    }
                }

                for (Account account : accountMap.values()) {
                    accountService.update(account);
                }
            } else {
                //审核拒绝
                auditReject(bidRequest);
            }
            this.update(bidRequest);
        }

    }

    private List<PaymentSchedule> createPaymentSchedule(BidRequest bidRequest) {

        List<PaymentSchedule> paymentSchedules = new ArrayList<PaymentSchedule>();
        PaymentSchedule ps;
        BigDecimal principalTemp = BigDecimal.ZERO;
        BigDecimal interestTemp = BigDecimal.ZERO;
        for (int i =0;i<bidRequest.getMonthes2Return();i++) {

            ps = new PaymentSchedule();
            ps.setBidRequestId(bidRequest.getId());
            ps.setBidRequestTitle(bidRequest.getTitle());
            ps.setBidRequestType(bidRequest.getBidRequestType());
            ps.setBorrowUser(bidRequest.getCreateUser());
            ps.setDeadLine(DateUtils.addMonths(bidRequest.getPublishTime(), i + 1));
            ps.setReturnType(bidRequest.getReturnType());
            ps.setMonthIndex(i+1);

            //判断是否是最后一期
            //不是
            if (i < bidRequest.getMonthes2Return() - 1) {

                //每月还款金额
                BigDecimal bigDecimal = CalculatetUtil.calMonthToReturnMoney(
                        bidRequest.getReturnType(),//还款方式
                        bidRequest.getBidRequestAmount(),//还款金额
                        bidRequest.getCurrentRate(),//还款年化利率
                        i + 1,//第几期还款
                        bidRequest.getMonthes2Return());//还多少期
                ps.setTotalAmount(bigDecimal);

                //每月还款利息
                BigDecimal monthBigDecimal = CalculatetUtil.calMonthlyInterest(
                        bidRequest.getReturnType(),//还款方式
                        bidRequest.getBidRequestAmount(),//还款金额
                        bidRequest.getCurrentRate(),//还款年化利率
                        i + 1,//第几期还款
                        bidRequest.getMonthes2Return());
                ps.setInterest(monthBigDecimal);
                //该期还款本金=该期还款金额-该期利息
                ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));

                //前n-1期之和
                interestTemp = interestTemp.add(ps.getInterest());
                principalTemp = principalTemp.add(ps.getPrincipal());
            } else {//是最后一期
                //该期还款本金 = 借款本金 - 前n-1期的本金之和
                ps.setPrincipal(bidRequest.getBidRequestAmount().subtract(principalTemp));
                //该期利息= 借款的利息-前n-1期的利息之和
                ps.setInterest(bidRequest.getTotalRewardAmount().subtract(interestTemp));
                //该期总的还款金额=该期本金+该期利息
                ps.setTotalAmount(ps.getInterest().add(ps.getPrincipal()));
            }
            paymentScheduleService.save(ps);
            //创建还款明细对象
            createPaymentScheduleDetailList(ps, bidRequest); //对象引用,该方法ps(还款对象)中存放psd(还款明细对象)

            paymentSchedules.add(ps);
        }
            return paymentSchedules;
    }

    private void createPaymentScheduleDetailList(PaymentSchedule ps, BidRequest bidRequest) {

        PaymentScheduleDetail psd;
        Bid bid;
        BigDecimal principalTemp = BigDecimal.ZERO;
        BigDecimal interestTemp = BigDecimal.ZERO;
        for (int i= 0;i< bidRequest.getBids().size();i++) {
            bid = bidRequest.getBids().get(i);
            psd = new PaymentScheduleDetail();

            psd.setBidAmount(bid.getAvailableAmount());
            psd.setBidId(bid.getId());
            psd.setBidRequestId(bidRequest.getId());
            psd.setBorrowUser(bidRequest.getCreateUser());
            psd.setDeadLine(ps.getDeadLine());
            psd.setInvestorId(bid.getBidUser().getId());
            psd.setMonthIndex(ps.getMonthIndex());
            psd.setPaymentScheduleId(ps.getId());
            psd.setReturnType(ps.getReturnType());

            //判断是否是最后一个投标人
            if (i < bidRequest.getBids().size() - 1) {
                //不是最后投标人

                //(该期投标金额/借款金额)
                BigDecimal bidRate = bid.getAvailableAmount().divide(bidRequest.getBidRequestAmount(), BidConst.CAL_SCALE, RoundingMode.HALF_UP);
                //该期还款明细的本金 =(该期投标金额/借款金额)*该期还款的本金
                psd.setPrincipal(bidRate.multiply(ps.getPrincipal()).setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP));
                //该期还款明细的利息 =(该期投标金额/借款金额)*该期还款的利息
                psd.setInterest(bidRate.multiply(ps.getInterest()).setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP));
                //该期明细的总金额  =该期还款明细的金额+该期明细的利息
                psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));

                //前n-1期之和
                principalTemp = principalTemp.add(psd.getPrincipal());
                interestTemp = interestTemp.add(psd.getInterest());

            } else {
                //是最后一个投资人
                //该还款明细的本金=该期还款的本金-(前n-1期投资人的本金之和)
                psd.setPrincipal(ps.getPrincipal().subtract(principalTemp));
                psd.setInterest(ps.getInterest().subtract(interestTemp));
                psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));
            }
            paymentScheduleDetailService.save(psd);
            ps.getDetails().add(psd);
        }


    }

}
