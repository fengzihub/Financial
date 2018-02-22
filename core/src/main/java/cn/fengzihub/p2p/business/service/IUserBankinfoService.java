package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.UserBankinfo;

/**
 * Created by Administrator on 2018.02.22.
 */
public interface IUserBankinfoService {
    int save(UserBankinfo userBankinfo);

    UserBankinfo getByUserinfoId(Long userinfoId);

    /**
     * 绑定银行卡
     * @param userBankinfo
     */
    void bind(UserBankinfo userBankinfo);

}
