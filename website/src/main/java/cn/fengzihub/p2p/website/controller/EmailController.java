package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.service.IEmailService;
import cn.fengzihub.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.11.
 */
@Controller
public class EmailController {
    @Autowired
    private IEmailService emailService;

    @RequestMapping("/sendEmail")
    @ResponseBody
    public JSONResult sendEmail(String email) {
        JSONResult jsonResult = new JSONResult();
        try {
            emailService.sendEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }



}
