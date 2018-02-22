package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.business.domain.UserBankinfo;
import cn.fengzihub.p2p.business.mapper.UserBankinfoMapper;
import cn.fengzihub.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.22.
 */
@Service
@Transactional
public class UserBankinfoServiceImpl implements IUserBankinfoService {
    @Autowired
    private UserBankinfoMapper userBankinfoMapper;
    @Autowired
    private IUserinfoService userinfoService;

    @Override
    public int save(UserBankinfo userBankinfo) {
        userBankinfoMapper.insert(userBankinfo);
        return 0;
    }

    @Override
    public UserBankinfo getByUserinfoId(Long userinfoId) {
        return userBankinfoMapper.selectByUserId(userinfoId);
    }

    @Override
    public void bind(UserBankinfo userBankinfo) {

        Userinfo current = userinfoService.getCurrent();
        if (current.getIsRealAuth() && !current.getIsBindBank()) {
            UserBankinfo bankinfo = new UserBankinfo();
            bankinfo.setAccountName(current.getRealName());
            bankinfo.setAccountNumber(userBankinfo.getAccountNumber());
            bankinfo.setBankName(userBankinfo.getBankName());
            bankinfo.setForkName(userBankinfo.getForkName());
            bankinfo.setUserinfoId(current.getId());
            this.save(bankinfo);

            current.addSate(BitStatesUtils.OP_BIND_BANK);
            userinfoService.update(current);

        }
    }
}
