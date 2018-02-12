package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.ISystemDictionaryItemService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.12.
 */
@Controller
public class BasicInfoController {
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;

    @RequestMapping("/basicInfo")
    public String basicInfoPage(Model model) {
        model.addAttribute("userinfo", userinfoService.getCurrent());
        //educationBackgrounds
        model.addAttribute("educationBackgrounds",systemDictionaryItemService.queryListByParentSn("educationBackground"));
        model.addAttribute("incomeGrades",systemDictionaryItemService.queryListByParentSn("incomeGrade"));
        model.addAttribute("marriages",systemDictionaryItemService.queryListByParentSn("marriage"));
        model.addAttribute("kidCounts",systemDictionaryItemService.queryListByParentSn("kidCount"));
        model.addAttribute("houseConditions",systemDictionaryItemService.queryListByParentSn("houseCondition"));

        return "userInfo";
    }

    @RequestMapping("/basicInfo_save")
    @ResponseBody
    public JSONResult basicInfoSave(Userinfo userinfo) {
        JSONResult jsonResult = new JSONResult();
        try {
            userinfoService.basicInfoSave(userinfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }


}
