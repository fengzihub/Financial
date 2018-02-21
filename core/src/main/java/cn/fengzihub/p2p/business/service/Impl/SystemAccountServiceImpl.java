package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.SystemAccount;
import cn.fengzihub.p2p.business.mapper.SystemAccountMapper;
import cn.fengzihub.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.21.
 */
@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService {
    @Autowired
    private SystemAccountMapper systemAccountMapper;
    @Override
    public int save(SystemAccount systemAccount) {
        systemAccountMapper.insert(systemAccount);
        return 0;
    }

    @Override
    public int update(SystemAccount systemAccount) {
        int count = systemAccountMapper.updateByPrimaryKey(systemAccount);
        if (count <= 0) {
            throw new RuntimeException("乐观锁异常" + systemAccount.getId());
        }
        return count;
    }

    @Override
    public SystemAccount getCurrent() {
        return systemAccountMapper.selectByCurrent();
    }

    @Override
    public void initSystemAccount() {
        SystemAccount systemAccount = this.getCurrent();
        if (systemAccount == null) {
            systemAccount = new SystemAccount();
            this.save(systemAccount);
        }
    }
}
