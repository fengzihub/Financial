package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.query.RealAuthQuerObject;
import cn.fengzihub.p2p.base.service.IRealAuthService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.16.
 */
@Controller
public class RealAuthController {
    @Autowired
    private IRealAuthService realAuthService;

    @RequestMapping("/realAuthList")
    public String realAuthList() {
        return "/realAuth/list";
    }


    @RequestMapping("/realAuthPage")
    @ResponseBody
    public PageResult realAuthPage(RealAuthQuerObject qo) {
        return realAuthService.queryPage(qo);
    }


    @RequestMapping("/realAuth_audit")
    @ResponseBody
    public JSONResult realAuthAudit(Long id,int state,String remark) {

        JSONResult jsonResult = new JSONResult();
        try {
            realAuthService.auth(id,state,remark);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }
}
