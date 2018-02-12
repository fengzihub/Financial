package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.domain.SystemDictionaryItem;
import cn.fengzihub.p2p.base.query.SystemDictionaryItemQueryObject;
import cn.fengzihub.p2p.base.service.ISystemDictionaryItemService;
import cn.fengzihub.p2p.base.service.ISystemDictionaryService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.12.
 */
@Controller
public class SystemDictionaryItemController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @RequestMapping("/systemDictionaryItemIndex")
    public String systemDictionaryItemIndexPage(Model model) {
        model.addAttribute("systemDictionaryGroups",systemDictionaryService.selectAll());
        return "/systemdic/systemDictionaryItem_list";
    }

    @RequestMapping("/systemDictionaryItemPage")
    @ResponseBody
    public PageResult systemDictionaryItemPage(SystemDictionaryItemQueryObject qo) {
        return systemDictionaryItemService.pageResult(qo);
    }



    @RequestMapping("/systemDictionaryItemUpdate")
    @ResponseBody
    public JSONResult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem) {
        JSONResult jsonResult = new JSONResult();
        try {
            systemDictionaryItemService.update(systemDictionaryItem);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }
    @RequestMapping("/systemDictionaryItemSave")
    @ResponseBody
    public JSONResult systemDictionaryItemSave(SystemDictionaryItem systemDictionaryItem) {
        JSONResult jsonResult = new JSONResult();
        try {
            systemDictionaryItemService.save(systemDictionaryItem);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }

}
