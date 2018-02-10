package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.Logininfo;
import cn.fengzihub.p2p.base.service.ILogininfoService;
import cn.fengzihub.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.07.
 */
@Controller
public class RegisterController {
    @Autowired
    private ILogininfoService logininfoService;

    //注册页面
    @RequestMapping("/userRegister")
    @ResponseBody
    public JSONResult userRegister(String username, String password) {
        JSONResult jsonResult = new JSONResult();
        try {
            logininfoService.register(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


    //校验注册账号是否存在
    @RequestMapping("/checkUsername")
    @ResponseBody
    public boolean checkUsername(String username) {
        return logininfoService.checkUsername(username);
    }

    //登录
    @RequestMapping("/userLogin")
    @ResponseBody
    public JSONResult userLogin(String username, String password) {
        JSONResult jsonResult = new JSONResult();
        Logininfo logininfo = logininfoService.userLogin(username, password,Logininfo.USERTYPE_USER);
        if (logininfo == null) {
            jsonResult.mark("账号或密码错误");
        }
        return jsonResult;
    }


}
