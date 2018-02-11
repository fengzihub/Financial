package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.MailVerify;
import cn.fengzihub.p2p.base.mapper.MailVerifyMapper;
import cn.fengzihub.p2p.base.service.IMailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.11.
 */
@Service@Transactional
public class MailVerifyServiceIMpl implements IMailVerifyService {
    @Autowired
    private MailVerifyMapper mailVerifyMapper;

    @Override
    public int save(MailVerify mailVerify) {
        mailVerifyMapper.insert(mailVerify);
        return 0;
    }

    @Override
    public int update(MailVerify mailVerify) {
        mailVerifyMapper.updateByPrimaryKey(mailVerify);
        return 0;
    }

    @Override
    public MailVerify get(String key) {
        return mailVerifyMapper.get(key);
    }

}
