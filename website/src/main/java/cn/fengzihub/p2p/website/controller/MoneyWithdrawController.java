package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.business.service.IMoneyWithdrawService;
import cn.fengzihub.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 提现
 */
@Controller
public class MoneyWithdrawController {

    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IUserBankinfoService bankinfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IMoneyWithdrawService moneyWithdrawService;


    @RequestMapping("/moneyWithdraw")
    public String moneyWithdraw(Model model) {
        Userinfo current = userinfoService.getCurrent();
        model.addAttribute("haveProcessing", current.gethasMoneywithProcess());
        model.addAttribute("bankInfo", bankinfoService.getByUserinfoId(current.getId()));
        model.addAttribute("account", accountService.getCurrent());

        return "moneyWithdraw_apply";
    }

    @RequestMapping("/moneyWithdraw_apply")
    @ResponseBody
    public JSONResult moneyWithdraw_apply(BigDecimal moneyAmount) {
        JSONResult jsonResult = new JSONResult();
        try {
            moneyWithdrawService.applay(moneyAmount);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


}
