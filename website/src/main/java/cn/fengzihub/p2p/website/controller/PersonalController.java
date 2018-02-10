package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.website.util.RequirelLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.07.
 */
@Controller
public class PersonalController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;
    @RequestMapping("/personal")
    @RequirelLogin
    public String personalPage(Model model) {

        model.addAttribute("account", accountService.getCurrent());
        model.addAttribute("userinfo", userinfoService.getCurrent());
        return "personal";
    }

    //手机验证
    @RequestMapping("/bindPhone")
    @ResponseBody
    public JSONResult bindPhone(String phoneNumber,String verifyCode) {
        JSONResult jsonResult = new JSONResult();
        try {
            userinfoService.bindPhone(phoneNumber, verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }

}
