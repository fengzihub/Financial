package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.RealAuth;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IRealAuthService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.website.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018.02.12.
 */
@Controller
public class RealAuthController {
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserinfoService userinfoService;


    @Value("${file.path}")
    private String filePath;

    @RequestMapping("/realAuth")
    public String realAuthPage(Model model) {
        //获取userinfo的realAuthid查询对应的实名认证对象跳转到realAuth_result页面
        Userinfo userinfo = userinfoService.getCurrent();
        if (userinfo.getIsRealAuth()) {
            //如果有,根据userinfo中的realAuthid查询对应的实名认证
            RealAuth realAuth = realAuthService.get(userinfo.getRealAuthId());
            model.addAttribute("realAuth", realAuth);
            model.addAttribute("auditing", false);
            return "realAuth_result";
        } else {
            //如果没有实名认证码
            //判断userinfo对象中的realAuth是否为null
            if (userinfo.getRealAuthId() == null) {
                return "realAuth";
            } else {
                //跳转到待审核状态
                model.addAttribute("auditing", true);
                return "realAuth_result";
            }
        }
    }


    @RequestMapping("/realAuth_save")
    @ResponseBody
    public JSONResult realAuthSave(RealAuth realAuth) {
        JSONResult jsonResult = new JSONResult();
        try {
            realAuthService.realAuthSave(realAuth);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


    @RequestMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(MultipartFile image) {
        return UploadUtil.upload(image, filePath);
    }



}
