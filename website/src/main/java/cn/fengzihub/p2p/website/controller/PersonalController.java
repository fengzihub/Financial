package cn.fengzihub.p2p.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018.02.07.
 */
@Controller
public class PersonalController {

    @RequestMapping("/personal")
    public String personalPage() {
        return "personal";
    }
}
