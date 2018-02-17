package cn.fengzihub.p2p.mgrsite.controller;

import cn.fengzihub.p2p.base.query.UserFileQueryObject;
import cn.fengzihub.p2p.base.service.IUserFileService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.base.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018.02.17.
 */
@Controller
public class UserFileController {
    @Autowired
    private IUserFileService userFileService;

    @RequestMapping("/userFileList")
    public String userFileList() {
        return "/userFileAuth/list";
    }

    @RequestMapping("/userFilePage")
    @ResponseBody
    public PageResult userFilePage(UserFileQueryObject qo) {
        qo.setSelectFileType(true);
        return userFileService.queryPage(qo);
    }

    @RequestMapping("/userFile_audit")
    @ResponseBody
    public JSONResult realAuthAudit(Long id, int state,int score, String remark) {

        JSONResult jsonResult = new JSONResult();
        try {
            userFileService.audit(id,state,score,remark);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }



}
