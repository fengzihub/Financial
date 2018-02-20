package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.business.domain.RechargeOffline;
import cn.fengzihub.p2p.business.service.IPlatformBankinfoService;
import cn.fengzihub.p2p.business.service.IRechargeOfflineService;
import cn.fengzihub.p2p.website.util.RequirelLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.20.
 */
@Controller
public class RechargeController {
    @Autowired
    private IPlatformBankinfoService platformBankinfoService;
    @Autowired
    private IRechargeOfflineService rechargeOfflineService;
    @RequestMapping("/recharge")
    public String rechargeOffLinePage(Model model) {
        model.addAttribute("banks", platformBankinfoService.selectAll());
        return "recharge";
    }

    @RequestMapping("/recharge_save")
    @RequirelLogin
    @ResponseBody
    public JSONResult rechargeSave(RechargeOffline rechargeOffline) {
        JSONResult jsonResult = new JSONResult();
        try {
            //线下充值申请
            rechargeOfflineService.apply(rechargeOffline);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


}
