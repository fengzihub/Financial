package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.SystemAccount;

/**
 * Created by Administrator on 2018.02.21.
 */
public interface ISystemAccountService {
    int save(SystemAccount systemAccount);

    int update(SystemAccount systemAccount);

    SystemAccount getCurrent();

    void initSystemAccount();


}
