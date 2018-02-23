package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.23.
 */
@Controller
public class expBidRequestController {
    @Autowired
    private IBidRequestService bidRequestService;


    @RequestMapping("/expBidRequestPublishPage")
    public String expBidRequestPublishPage(Model model) {

        model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BIDREQUEST_AMOUNT);
        model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
        return "/expbidrequest/expbidrequestpublish";
    }


    //发布一个体验标
    @RequestMapping("/expBidRequestPublish")
    @ResponseBody
    public JSONResult publish(BidRequest bidRequest) {
        bidRequestService.publish(bidRequest);
        return new JSONResult();
    }

    @RequestMapping("/expBidRequestPublishList")
    public String expBidRequestPublishList() {
        //TODO 体验金列表???
        return "";
    }



}
