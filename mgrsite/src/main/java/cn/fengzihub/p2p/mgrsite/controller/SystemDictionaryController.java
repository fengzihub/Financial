package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.domain.SystemDictionary;
import cn.fengzihub.p2p.base.query.SystemDictionaryQueryObject;
import cn.fengzihub.p2p.base.service.ISystemDictionaryService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.12.
 */
@Controller
public class SystemDictionaryController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

  /*  @RequestMapping("/systemDictionaryIndex")
    public String systemDictionaryIndexPage(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
        PageInfo pageInfo = systemDictionaryService.queryPage(qo);
        model.addAttribute("pageResult", pageInfo);
        return "/systemdic/systemDictionary_list";
    }*/
    @RequestMapping("/systemDictionaryIndex")
    public String systemDictionaryIndexPage() {
        return "/systemdic/systemDictionary_list";
    }

    @RequestMapping("/systemDictionaryPage")
    @ResponseBody
    public PageResult systemDictionaryPage(SystemDictionaryQueryObject qo) {
        return systemDictionaryService.pageResult(qo);
    }

    @RequestMapping("/systemDictionaryUpdate")
    @ResponseBody
    public JSONResult systemDictionaryUpdate(SystemDictionary systemDictionary) {
        JSONResult jsonResult = new JSONResult();
        try {
            systemDictionaryService.update(systemDictionary);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }
    @RequestMapping("/systemDictionarySave")
    @ResponseBody
    public JSONResult systemDictionarySave(SystemDictionary systemDictionary) {
        JSONResult jsonResult = new JSONResult();
        try {
            systemDictionaryService.save(systemDictionary);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }




}
