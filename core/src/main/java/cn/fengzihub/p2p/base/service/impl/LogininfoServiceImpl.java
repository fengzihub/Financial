package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.domain.IpLog;
import cn.fengzihub.p2p.base.domain.Logininfo;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.mapper.LogininfoMapper;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IIpLogService;
import cn.fengzihub.p2p.base.service.ILogininfoService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.MD5;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018.02.07.
 */
@Service
@Transactional
public class LogininfoServiceImpl implements ILogininfoService {
    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IIpLogService ipLogService;

    //注册
    @Override
    public Logininfo register(String username, String password) {

        //先从数据库查,存在就抛出异常
        int count = logininfoMapper.selectCountByUsername(username);
        if (count > 0) {
            throw new RuntimeException("账号已经存在");
        }
        //不存在就保存入库
        Logininfo logininfo = new Logininfo();
        logininfo.setUsername(username);
        logininfo.setPassword(MD5.encode(password));
        logininfo.setState(Logininfo.STATE_NORMAL);
        logininfo.setUserType(Logininfo.USERTYPE_USER);
        logininfoMapper.insert(logininfo);
        //用户注册的时候就把Account和Userinfo创建出来
        Account account = new Account();
        account.setId(logininfo.getId());
        accountService.save(account);
        Userinfo userinfo = new Userinfo();
        userinfo.setId(logininfo.getId());
        userinfoService.save(userinfo);
        return logininfo;
    }

    @Override
    public Boolean checkUsername(String username) {
        int count = logininfoMapper.selectCountByUsername(username);
        if (count > 0) {
            return false;
        }
        return true;
    }

    //登录业务方法
    public Logininfo userLogin(String username, String password, int userType) {
        //根据用户名,密码去数据库查询
        password = MD5.encode(password);
        Logininfo logininfo = logininfoMapper.login(username, password, userType);

        //登录日志待完善 TODO
        IpLog ipLog = new IpLog();
        ipLog.setLoginTime(new Date());
        ipLog.setUsername(username);
        ipLog.setIp(UserContext.getIp());
        ipLog.setUserType(userType);

        if (logininfo != null) {
            //登录成功保存日志
            ipLog.setState(IpLog.LOGIN_SUCCESS);
            ipLogService.save(ipLog);
            //说明登录成功 ,把登录对象存放到session中
            UserContext.setCurrent(logininfo);
        } else {
            ipLog.setState(IpLog.LOGIN_FAILED);
            ipLogService.save(ipLog);
        }
        return logininfo;
    }

    @Override
    public void initAdmin() {
        int count = logininfoMapper.queryCountByUserType(Logininfo.USERTYPE_MANAGER);
        if (count <= 0) {
            Logininfo logininfo = new Logininfo();
            logininfo.setState(Logininfo.STATE_NORMAL);
            logininfo.setUserType(Logininfo.USERTYPE_MANAGER);
            logininfo.setUsername(BidConst.MANAGER_ACCOUNT);
            logininfo.setPassword(MD5.encode(BidConst.MANAGER_PASSWORD));
            logininfoMapper.insert(logininfo);
        }

    }

    @Override
    public List<Map<String, Object>> autocomplate(String keyword) {
        return logininfoMapper.autocomplate(keyword,Logininfo.USERTYPE_USER);
    }


}
