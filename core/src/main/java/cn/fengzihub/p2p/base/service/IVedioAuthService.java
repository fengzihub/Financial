package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.VedioAuth;
import cn.fengzihub.p2p.base.query.VedioAuthQueryObject;
import cn.fengzihub.p2p.base.util.PageResult;

/**
 * Created by Administrator on 2018.02.18.
 */

public interface IVedioAuthService {

    int save(VedioAuth vedioAuth);

    int update(VedioAuth vedioAuth);

    VedioAuth get(Long id);


    PageResult queryPage(VedioAuthQueryObject qo);

    /**
     * 视频认证审核
     * @param id
     * @param state
     * @param remark
     */
    void audit(Long id, int state, String remark);
}
