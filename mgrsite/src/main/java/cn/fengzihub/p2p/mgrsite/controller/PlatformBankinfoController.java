package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.PlatformBankinfo;
import cn.fengzihub.p2p.business.query.PlatformDescriptionQueryObject;
import cn.fengzihub.p2p.business.service.IPlatformBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.20.
 */
@Controller
public class PlatformBankinfoController {
    @Autowired
    private IPlatformBankinfoService platformBankinfoService;

    @RequestMapping("/platformBankinfoList")
    public String platformBankinfoList() {
        return "/platformbankinfo/platformBankinfoList";
    }

    @RequestMapping("/platformBankinfoPage")
    @ResponseBody
    public PageResult platformBankinfoPage(PlatformDescriptionQueryObject qo) {
        return platformBankinfoService.queryPage(qo);
    }


    @RequestMapping("/platformBankinfoSave")
    @ResponseBody
    public JSONResult platformBankinfoSave(PlatformBankinfo platformBankinfo) {
        JSONResult jsonResult = new JSONResult();
        try {
            platformBankinfoService.save(platformBankinfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }
    @RequestMapping("/platformBankinfoUpdate")
    @ResponseBody
    public JSONResult platformBankinfoUpdate(PlatformBankinfo platformBankinfo) {
        JSONResult jsonResult = new JSONResult();
        try {
            platformBankinfoService.update(platformBankinfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


}
