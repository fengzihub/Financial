package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.Userinfo;

/**
 * Created by Administrator on 2018.02.10.
 */
public interface IUserinfoService {
    int save(Userinfo userinfo);

    int update(Userinfo userinfo);

    Userinfo get(Long id);

    Userinfo getCurrent();

    /**
     * 绑定手机
     * @param phoneNumber
     * @param verifyCode
     */
    void bindPhone(String phoneNumber, String verifyCode);

    /**
     * 绑定邮箱验证
     * @param key
     */
    void bindEmail(String key);

    /**
     * 填写个人资料
     * @param userinfo
     */
    void basicInfoSave(Userinfo userinfo);
}
