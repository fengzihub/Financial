package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.service.IVerifyCodeService;
import cn.fengzihub.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.10.
 */
@Controller
public class VerifyCodeController {
    @Autowired
    private IVerifyCodeService verifyCodeService;

    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber) {
        JSONResult jsonResult = new JSONResult();
        try {
            //后台发送验证码 TODO
            verifyCodeService.sendVerifyCode(phoneNumber);

        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }

}
