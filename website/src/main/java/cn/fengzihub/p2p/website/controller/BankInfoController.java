package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.UserBankinfo;
import cn.fengzihub.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018.02.22.
 */
@Controller
public class BankInfoController {
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IUserBankinfoService userBankinfoService;

    @RequestMapping("/bankInfo")
    public String bankinfoPage(Model model) {

        Userinfo current = userinfoService.getCurrent();
        if (current.getIsBindBank()) {
            //已经绑定银行卡
            model.addAttribute("bankInfo", userBankinfoService.getByUserinfoId(UserContext.getCurrent().getId()));
            return "bankInfo_result";
        } else {
            model.addAttribute("userinfo", current);
            return "bankInfo";
        }
    }


    @RequestMapping("/bankInfo_save")
    public String bindBankinfo(UserBankinfo userBankinfo) {
        userBankinfoService.bind(userBankinfo);
        return "redirect:/bankInfo";
    }

}
