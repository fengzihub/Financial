package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.query.RechargeofflineQueryObject;
import cn.fengzihub.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.20.
 */
@Controller
public class RechargeofflineController {
    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    @RequestMapping("/rechargeofflineList")
    public String rechargeofflineList() {
        return "/rechargeoffline/list";
    }


    @RequestMapping("/rechargeofflinePage")
    @ResponseBody
    public PageResult rechargeofflinePage(RechargeofflineQueryObject qo) {
        return rechargeOfflineService.queryPage(qo);
    }


    @RequestMapping("/RechargeOffline_audit")
    @ResponseBody
    public JSONResult RechargeOfflineAudit(Long id,int state,String remark) {
        JSONResult jsonResult = new JSONResult();
        try {
            rechargeOfflineService.audit(id, state, remark);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


}
