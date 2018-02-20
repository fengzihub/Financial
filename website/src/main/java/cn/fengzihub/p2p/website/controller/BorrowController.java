package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IRealAuthService;
import cn.fengzihub.p2p.base.service.IUserFileService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.service.IBidRequestService;
import cn.fengzihub.p2p.website.util.RequirelLogin;
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
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;
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

    @RequestMapping("/borrowInfo")
    public String borrowInfo(Model model) {
        //是否已经登录
        if (UserContext.getCurrent() != null) {
            Userinfo userinfo = userinfoService.getCurrent();
            //判断是否满足借款条件
            if (bidRequestService.canApplyBorrow(userinfo)) {
                //判断用户是否有借款流程正在审核
                if (!userinfo.gethasBidRequestProcess()) {
                    //进入申请页面
                    model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BIDREQUEST_AMOUNT);
                    model.addAttribute("account", accountService.getCurrent());
                    model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
                    return "borrow_apply";
                } else {
                    //进入结果页面
                    return "borrow_apply_result";
                }
            }
        }
        return "redirect:/borrow";
    }

    @RequestMapping("/borrow_apply")
    @RequirelLogin
    public String borrowApply(BidRequest bidRequest) {
        //借款申请方法
        bidRequestService.apply(bidRequest);
        return "redirect:/borrowInfo";
    }


    @RequestMapping("/borrow_info")
    public String borrowInfoPage(Long id,Model model) {
        BidRequest bidRequest = bidRequestService.get(id);
        if (bidRequest != null) {
            Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
            model.addAttribute("userInfo", userinfo);
            model.addAttribute("bidRequest", bidRequest);
            model.addAttribute("realAuth",realAuthService.get(userinfo.getRealAuthId()));
            model.addAttribute("userFiles", userFileService.queryBuUserId(bidRequest.getCreateUser().getId()));
            //判断是否登录
            if (UserContext.getCurrent() == null) {
                model.addAttribute("self", false);
            } else {
                //判断当前登录用户是否是借款人
                if (UserContext.getCurrent().getId().equals(bidRequest.getCreateUser().getId())) {
                    model.addAttribute("self", true);
                } else {
                    model.addAttribute("self", false);
                    model.addAttribute("account", accountService.getCurrent());
                }
            }
        }
        return "/borrow_info";
    }



}
