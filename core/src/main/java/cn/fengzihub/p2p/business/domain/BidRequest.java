package cn.fengzihub.p2p.business.domain;

import cn.fengzihub.p2p.base.domain.BaseDomain;
import cn.fengzihub.p2p.base.domain.Logininfo;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.business.util.CalculatetUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018.02.19.
 */
@Getter
@Setter
public class BidRequest extends BaseDomain {
    private int version;// 版本号
    private int returnType;// 还款类型(等额本息)
    private int bidRequestType;// 借款类型(信用标)
    private int bidRequestState;// 借款状态
    private BigDecimal bidRequestAmount;// 借款总金额
    private BigDecimal currentRate;// 年化利率
    private BigDecimal minBidAmount;// 最小投標金额
    private int monthes2Return;// 还款月数
    private int bidCount = 0;// 已投标次数(冗余)
    private BigDecimal totalRewardAmount;// 总回报金额(总利息)
    private BigDecimal currentSum = BidConst.ZERO;// 当前已投标总金额
    private String title;// 借款标题
    private String description;// 借款描述
    private String note;// 风控意见
    private Date disableDate;// 招标截止日期
    private int disableDays;// 招标天数
    private Logininfo createUser;// 借款人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date applyTime;// 申请时间
    private Date publishTime;// 发标时间
    private List<Bid> bids = new ArrayList<Bid>();// 针对该借款的投标



    public String getReturnTypeDisplay() {
        return this.returnType == BidConst.RETURN_TYPE_MONTH_INTEREST ? "按月到期" : "按月分期";
    }

    public String getBidRequestStateDisplay() {
        switch (this.bidRequestState) {
            case BidConst.BIDREQUEST_STATE_PUBLISH_PENDING:
                return "待发布";
            case BidConst.BIDREQUEST_STATE_BIDDING:
                return "招标中";
            case BidConst.BIDREQUEST_STATE_UNDO:
                return "已撤销";
            case BidConst.BIDREQUEST_STATE_BIDDING_OVERDUE:
                return "流标";
            case BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1:
                return "满标一审";
            case BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2:
                return "满标二审";
            case BidConst.BIDREQUEST_STATE_REJECTED:
                return "满标审核被拒";
            case BidConst.BIDREQUEST_STATE_PAYING_BACK:
                return "还款中";
            case BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK:
                return "完成";
            case BidConst.BIDREQUEST_STATE_PAY_BACK_OVERDUE:
                return "逾期";
            case BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE:
                return "发标拒绝";
            default:
                return "";
        }
    }

    public BigDecimal getRemainAmount() {
        return this.bidRequestAmount.subtract(this.currentSum);
    }

    public BigDecimal getPersent() {
        return this.currentSum.multiply(CalculatetUtil.ONE_HUNDRED).
                divide(this.bidRequestAmount).setScale(2, RoundingMode.HALF_UP);
    }

}
