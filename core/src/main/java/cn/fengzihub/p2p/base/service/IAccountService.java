package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.Account;

/**
 * Created by Administrator on 2018.02.10.
 */
public interface IAccountService {
    int save(Account account);

    int update(Account account);

    Account get(Long id);

    Account getCurrent();
}
