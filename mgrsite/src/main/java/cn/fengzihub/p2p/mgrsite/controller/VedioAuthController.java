package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.query.VedioAuthQueryObject;
import cn.fengzihub.p2p.base.service.ILogininfoService;
import cn.fengzihub.p2p.base.service.IVedioAuthService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018.02.18.
 */
@Controller
public class VedioAuthController {
    @Autowired
    private IVedioAuthService vedioAuthService;
    @Autowired
    private ILogininfoService logininfoService;

    @RequestMapping("/vedioAuthList")
    public String vedioAuthList() {
        return "/vedioAuth/list";
    }

    @RequestMapping("/vedioAuthPage")
    @ResponseBody
    public PageResult vedioAuthPage(VedioAuthQueryObject qo) {
        return vedioAuthService.queryPage(qo);
    }

    @RequestMapping("/vedioAuth_audit")
    @ResponseBody
    public JSONResult vedioAuth(Long id,int state,String remark) {
        JSONResult jsonResult = new JSONResult();
        try {
            vedioAuthService.audit(id,state,remark);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping("/vedioAuth_autocomplate")
    @ResponseBody
    public List<Map<String, Object>> autocomplate(String keyword) {
        System.out.println(logininfoService.autocomplate(keyword));

        return logininfoService.autocomplate(keyword);
    }

}
