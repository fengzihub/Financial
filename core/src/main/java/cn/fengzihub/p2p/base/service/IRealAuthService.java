package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.RealAuth;
import cn.fengzihub.p2p.base.query.RealAuthQuerObject;
import cn.fengzihub.p2p.base.util.PageResult;

/**
 * Created by Administrator on 2018.02.12.
 */
public interface IRealAuthService {
    int save(RealAuth realAuth);

    int update(RealAuth realAuth);

    RealAuth get(Long id);

    /**
     * 实名认证申请
     * @param realAuth
     */
    void realAuthSave(RealAuth realAuth);


    PageResult queryPage(RealAuthQuerObject qo);


    /**
     * 实名认证审核
     * @param
     */
    void auth(Long id, int state, String remark);

}
