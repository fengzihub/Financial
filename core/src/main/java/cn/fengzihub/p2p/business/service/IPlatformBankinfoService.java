package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.PlatformBankinfo;
import cn.fengzihub.p2p.business.query.PlatformDescriptionQueryObject;

import java.util.List;

/**
 * Created by Administrator on 2018.02.20.
 */
public interface IPlatformBankinfoService {
    int save(PlatformBankinfo platformBankinfo);

    int update(PlatformBankinfo platformBankinfo);

    PlatformBankinfo get(Long id);

    PageResult queryPage(PlatformDescriptionQueryObject qo);

    List<PlatformBankinfo> selectAll();

}
