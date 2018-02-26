package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;
import cn.fengzihub.p2p.business.query.PaymentScheduleQueryObject;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.19.
 */
public interface IBidRequestService {
    int save(BidRequest bidRequest);

    int update(BidRequest bidRequest);

    BidRequest get(Long id);

    /**
     * 判断用户是否满足借款的资格
     * @return
     */
    boolean canApplyBorrow(Userinfo userinfo);

    /**
     * 借款申请
     * @param bidRequest
     */
    void apply(BidRequest bidRequest);

    PageResult queryPage(BidRequestQueryObject qo);

    /**
     * 发标前审核
     * @param id
     * @param state
     * @param remark
     */
    void publishAudit(Long id, int state, String remark);

    PageInfo pageInfoPage(BidRequestQueryObject qo);

    /**
     * 投标
     * @param bidRequestId
     * @param amount
     */
    void bid(Long bidRequestId, BigDecimal amount);

    /**
     * 满标一审
     * @param id
     * @param state
     * @param remark
     */
    void audit1(Long id, int state, String remark);

    /**
     * 满标二审
     * @param id
     * @param state
     * @param remark
     */
    void audit2(Long id, int state, String remark);

    /**
     * 体验标
     * @param bidRequest
     */
    void publish(BidRequest bidRequest);

    PageResult querPaymentSchedule(PaymentScheduleQueryObject qo);
}
