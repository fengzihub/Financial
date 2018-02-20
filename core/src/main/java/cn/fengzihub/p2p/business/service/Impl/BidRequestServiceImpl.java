package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.domain.BidRequestAuditHistory;
import cn.fengzihub.p2p.business.mapper.BidRequestMapper;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;
import cn.fengzihub.p2p.business.service.IBidRequestAuditHistoryService;
import cn.fengzihub.p2p.business.service.IBidRequestService;
import cn.fengzihub.p2p.business.util.CalculatetUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
}
