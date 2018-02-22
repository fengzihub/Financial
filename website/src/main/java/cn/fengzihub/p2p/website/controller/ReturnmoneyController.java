package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.query.PaymentScheduleQueryObject;
import cn.fengzihub.p2p.business.service.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.22.
 */
@Controller
public class ReturnmoneyController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;
    @RequestMapping("/borrowBidReturn_list")
    public String borrowBidReturnPage(@ModelAttribute("qo") PaymentScheduleQueryObject qo, Model model) {

        model.addAttribute("account", accountService.getCurrent());
        qo.setUserId(UserContext.getCurrent().getId());
        model.addAttribute("pageResult", paymentScheduleService.queryPage(qo));
        return "/returnmoney_list";
    }


    @RequestMapping("/returnMoney")
    @ResponseBody
    public JSONResult returnMoneyPage(Long id) {
        JSONResult jsonResult = new JSONResult();
        try {
            paymentScheduleService.returnMoney(id);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }



}
