package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;
import cn.fengzihub.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018.02.20.
 */

@Controller
public class InvestController {
    @Autowired
    private IBidRequestService bidRequestService;
    @RequestMapping("/invest")
    public String investPage() {
        return "invest";
    }

    @RequestMapping("/invest_list")
    public String investList(BidRequestQueryObject qo,Model model) {

        qo.setStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK,BidConst.BIDREQUEST_STATE_PAYING_BACK});
        qo.setOrderByCondition("ORDER BY br.bidRequestState ASC");
        model.addAttribute("pageResult", bidRequestService.pageInfoPage(qo));
        return "invest_list";
    }


}
