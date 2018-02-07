package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.Logininfo;
import cn.fengzihub.p2p.base.mapper.LogininfoMapper;
import cn.fengzihub.p2p.base.service.ILogininfoService;
import cn.fengzihub.p2p.base.util.MD5;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.07.
 */
@Service
@Transactional
public class LogininfoServiceImpl implements ILogininfoService {
    @Autowired
    private LogininfoMapper logininfoMapper;

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
        logininfoMapper.insert(logininfo);
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
    public Logininfo userLogin(String username, String password) {
        //根据用户名,密码去数据库查询
        password = MD5.encode(password);
        Logininfo logininfo = logininfoMapper.login(username, password);

        //登录日志待完善 TODO

        if (logininfo != null) {
            //说明登录成功 ,把登录对象存放到session中
            UserContext.setCurrent(logininfo);
        }
        return logininfo;
    }


}
