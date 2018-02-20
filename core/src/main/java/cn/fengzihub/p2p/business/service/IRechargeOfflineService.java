package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.RechargeOffline;
import cn.fengzihub.p2p.business.query.RechargeofflineQueryObject;

/**
 * Created by Administrator on 2018.02.20.
 */
public interface IRechargeOfflineService {
    int save(RechargeOffline rechargeOffline);

    int update(RechargeOffline rechargeOffline);

    RechargeOffline get(Long id);

    /**
     * 线下充值申请
     * @param rechargeOffline
     */
    void apply(RechargeOffline rechargeOffline);

    PageResult queryPage(RechargeofflineQueryObject qo);

    /**
     * 线下充值审核
     * @param id
     * @param state
     * @param remark
     */
    void audit(Long id, int state, String remark);
}
