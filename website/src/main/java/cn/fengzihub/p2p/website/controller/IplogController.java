package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.query.IplogQueryObject;
import cn.fengzihub.p2p.base.service.IIpLogService;
import cn.fengzihub.p2p.base.util.UserContext;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018.02.10.
 */
@Controller
public class IplogController {
    @Autowired
    private IIpLogService ipLogService;

    @RequestMapping("/ipLog")
    public String ipLog(@ModelAttribute("qo") IplogQueryObject qo, Model model) {
        //查询当前用户的登录信息
        qo.setUsername(UserContext.getCurrent().getUsername());
        PageInfo pageInfo = ipLogService.queryPage(qo);
        model.addAttribute("pageResult", pageInfo);
        return "/iplog_list";
    }


}
