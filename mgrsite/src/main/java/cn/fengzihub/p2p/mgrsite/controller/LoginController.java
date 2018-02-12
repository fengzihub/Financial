package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.domain.Logininfo;
import cn.fengzihub.p2p.base.service.ILogininfoService;
import cn.fengzihub.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.10.
 */
@Controller
public class LoginController {
    @Autowired
    private ILogininfoService logininfoService;

    @RequestMapping("/managerLogin")
    @ResponseBody
    public JSONResult managerLogin(String username, String password) {
        JSONResult jsonResult = new JSONResult();
        Logininfo logininfo = logininfoService.userLogin(username, password, Logininfo.USERTYPE_MANAGER);
        if (logininfo == null) {
            jsonResult.mark("登录失败");
        }
        return jsonResult;
    }


    @RequestMapping("/index")
    public String indexPage() {
        return "main";
    }

}
