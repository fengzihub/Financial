package cn.fengzihub.p2p.website.controller;

import cn.fengzihub.p2p.base.domain.UserFile;
import cn.fengzihub.p2p.base.service.ISystemDictionaryItemService;
import cn.fengzihub.p2p.base.service.IUserFileService;
import cn.fengzihub.p2p.base.util.JSONResult;
import cn.fengzihub.p2p.website.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2018.02.17.
 */
@Controller
public class UserFileController {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private IUserFileService userFileService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;

    @RequestMapping("/userFile")
    public String userFilePage(Model model) {

        List<UserFile> userFileList = userFileService.selectFileTypeByCondition(false);
        if (userFileList.size() > 0) {
            model.addAttribute("userFiles", userFileList);
            model.addAttribute("fileTypes", systemDictionaryItemService.queryListByParentSn("userFileType"));
            return "userFiles_commit";
        } else {
            List<UserFile> selectFileList = userFileService.selectFileTypeByCondition(true);
            model.addAttribute("userFiles", selectFileList);
            return "userFiles";
        }

       /* model.addAttribute("userFiles", userFileService.querySelectFileTypeList());
        model.addAttribute("fileTypes", systemDictionaryItemService.queryListByParentSn("userFileType"));
        return "userFiles_commit";*/

    }

    @RequestMapping("/userFileUpload")
    @ResponseBody
    public String userFileUpload(MultipartFile file) {
        String imgPath = UploadUtil.upload(file, filePath);
        userFileService.upload(imgPath);
        return imgPath;
    }


    @RequestMapping("/userFile_selectType")
    @ResponseBody
    public JSONResult selectType(Long[] id, Long[] fileType) {
        JSONResult jsonResult = new JSONResult();
        try {
            userFileService.selectType(id, fileType);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.mark(e.getMessage());
        }
        return jsonResult;
    }

}
