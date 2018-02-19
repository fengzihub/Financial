package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IRealAuthService;
import cn.fengzihub.p2p.base.service.IUserFileService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.business.domain.BidRequest;
import cn.fengzihub.p2p.business.query.BidRequestQueryObject;
import cn.fengzihub.p2p.business.service.IBidRequestAuditHistoryService;
import cn.fengzihub.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.19.
 */
@Controller
public class BidRequestController {
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IBidRequestAuditHistoryService bidRequestAuditHistoryService;
    @Autowired
    private IUserFileService userFileService;
    @Autowired
    private IRealAuthService realAuthService;
    @RequestMapping("/bidRequestList")
    public String bidRequestList() {
        return "/bidRequest/audit1";
    }

    @RequestMapping("/bidRequestPage")
    @ResponseBody
    public PageResult bidRequestPage(BidRequestQueryObject qo) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        return bidRequestService.queryPage(qo);
    }

    @RequestMapping("/bidRequest_audit")
    @ResponseBody
    public JSONResult bidRequestAudit(Long id, int state, String remark) {
        JSONResult jsonResult = new JSONResult();
        try {
            bidRequestService.publishAudit(id, state, remark);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping("/borrow_info")
    public String borrow_info(Long id, Model model) {
        BidRequest bidRequest = bidRequestService.get(id);
        if (bidRequest != null) {
            Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
            model.addAttribute("userInfo", userinfo);
            model.addAttribute("bidRequest", bidRequest);
            model.addAttribute("realAuth",realAuthService.get(userinfo.getRealAuthId()));
            model.addAttribute("audits", bidRequestAuditHistoryService.queryByBidRequestId(bidRequest.getId()));
            model.addAttribute("userFiles",userFileService.queryBuUserId(bidRequest.getCreateUser().getId()));

        }
        return "/bidRequest/borrow_info";
    }


}
