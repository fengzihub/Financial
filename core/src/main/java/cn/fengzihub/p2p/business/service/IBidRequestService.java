package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;

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
}
