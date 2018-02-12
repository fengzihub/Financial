package cn.fengzihub.p2p.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018.02.12.
 */
@Controller
public class RealAuthController {


    @RequestMapping("/realAuth")
    public String realAuthPage() {
        return "realAuth";
    }



    //TODO 实名认证业务逻辑

}
