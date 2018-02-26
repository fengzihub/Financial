package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;
import cn.fengzihub.p2p.business.query.PaymentScheduleQueryObject;
import cn.fengzihub.p2p.business.service.IBidRequestService;
import cn.fengzihub.p2p.business.service.IPaymentScheduleService;
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
    @Autowired
    private IPaymentScheduleService paymentScheduleService;

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

        return "/expbidrequest/list";
    }
    @RequestMapping("/expbidrequestList")
    @ResponseBody
    public PageResult expbidrequestList(BidRequestQueryObject qo) {
        qo.setBidRequestType(BidConst.BIDREQUEST_TYPE_EXP);
        return bidRequestService.queryPage(qo);
    }



    //体验标还款页面
    @RequestMapping("/expBidRequestReturnList")
    public String expBidRequestReturnList() {
        return "/expbidrequest/expbidRequestReturnList";
    }
    @RequestMapping("/expBidRequestReturnListPage")
    @ResponseBody
    public PageResult expBidRequestReturnListPage(PaymentScheduleQueryObject qo) {
        qo.setBidRequestType(BidConst.BIDREQUEST_TYPE_EXP);
        return bidRequestService.querPaymentSchedule(qo);
    }


    //还款
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
