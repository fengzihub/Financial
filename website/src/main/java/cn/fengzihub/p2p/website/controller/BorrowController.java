package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018.02.12.
 */
@Controller
public class BorrowController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;

    @RequestMapping("/borrow")
    public String borrow(Model model) {
        //判断是否登录
        if (UserContext.getCurrent() == null) {
            return "redirect:borrowStatic.html";
        }
        model.addAttribute("account", accountService.getCurrent());
        model.addAttribute("userinfo", userinfoService.getCurrent());
        model.addAttribute("creditBorrowScore", BidConst.CREDIT_BORROW_SCORE);

        return "borrow";
    }

}
