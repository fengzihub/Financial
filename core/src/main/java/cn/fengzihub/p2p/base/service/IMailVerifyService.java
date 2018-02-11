package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.MailVerify;

/**
 * Created by Administrator on 2018.02.11.
 */
public interface IMailVerifyService {
    int save(MailVerify mailVerify);

    int update(MailVerify mailVerify);

    MailVerify get(String key);
}
