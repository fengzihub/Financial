package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.RealAuth;

/**
 * Created by Administrator on 2018.02.12.
 */
public interface IRealAuthService {
    int save(RealAuth realAuth);

    int update(RealAuth realAuth);

    RealAuth get(Long id);

}
