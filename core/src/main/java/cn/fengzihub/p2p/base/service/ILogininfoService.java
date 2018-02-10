package cn.fengzihub.p2p.base.service;

import cn.fengzihub.p2p.base.domain.Logininfo;

/**
 * Created by Administrator on 2018.02.07.
 */
public interface ILogininfoService {
    /**
     * 注册方法
     * @return
     */
    Logininfo register(String username,String password);

    Boolean checkUsername(String username);

    /**
     * 登录方法
     * @param username
     * @param password
     */
    Logininfo userLogin(String username, String password,int userType);

    /**
     * 初始化后台管理员
     */
    void initAdmin();
}
