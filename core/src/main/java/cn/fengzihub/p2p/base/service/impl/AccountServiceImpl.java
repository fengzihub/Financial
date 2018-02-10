package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.mapper.AccountMapper;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.10.
 */
@Service@Transactional
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public int save(Account account) {
        return accountMapper.insert(account);
    }

    @Override
    public int update(Account account) {
        int count = accountMapper.updateByPrimaryKey(account);
        if (count <= 0) {
            throw new RuntimeException("乐观锁异常"+account.getId());
        }
        return count;
    }

    @Override
    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public Account getCurrent() {
        return this.get(UserContext.getCurrent().getId());
    }
}
