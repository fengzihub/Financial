package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.query.MoneyWithdrawQuerObject;
import cn.fengzihub.p2p.business.service.IMoneyWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.23.
 */
@Controller
public class MoneyWithdrawController {

    @Autowired
    private IMoneyWithdrawService moneyWithdrawService;

    @RequestMapping("/moneyWithdrawList")
    public String moneyWithdrawList() {
        return "/moneywithdraw/list";
    }

    @RequestMapping("/moneyWithdrawPage")
    @ResponseBody
    public PageResult moneyWithdrawPage(MoneyWithdrawQuerObject qo) {
        return moneyWithdrawService.queryPage(qo);
    }

    @RequestMapping("/moneyWithdrawAuth_audit")
    @ResponseBody
    public JSONResult moneyWithdrawAuth_audit(Long id,int state,String remark) {
        JSONResult jsonResult = new JSONResult();
        try {
            moneyWithdrawService.audit(id, state, remark);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }




}
