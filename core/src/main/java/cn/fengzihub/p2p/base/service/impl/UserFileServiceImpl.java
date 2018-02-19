package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.SystemDictionaryItem;
import cn.fengzihub.p2p.base.domain.UserFile;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.mapper.UserFileMapper;
import cn.fengzihub.p2p.base.query.UserFileQueryObject;
import cn.fengzihub.p2p.base.service.IUserFileService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018.02.17.
 */
@Service
@Transactional
public class UserFileServiceImpl implements IUserFileService {
    @Autowired
    private UserFileMapper userFileMapper;
    @Autowired
    private IUserinfoService userinfoService;
    @Override
    public int save(UserFile userFile) {
        userFileMapper.insert(userFile);
        return 0;
    }

    @Override
    public int update(UserFile userFile) {
        userFileMapper.updateByPrimaryKey(userFile);
        return 0;
    }

    @Override
    public UserFile get(Long id) {
        return userFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public void upload(String imgPath) {
        //上传图片 创建userfile对象
        UserFile userFile = new UserFile();
        userFile.setImage(imgPath);
        userFile.setApplier(UserContext.getCurrent());
        userFile.setApplyTime(new Date());
        userFile.setState(UserFile.STATE_NORMAL);
        this.save(userFile);
    }

    @Override
    public List<UserFile> querySelectFileTypeList() {
        return userFileMapper.querySelectFileTypeList(UserContext.getCurrent().getId());
    }

    @Override
    public void selectType(Long[] ids, Long[] fileTypes) {
        if (ids != null && fileTypes != null
                && ids.length == fileTypes.length) {
            UserFile uf;
            SystemDictionaryItem fileType;
            for (int i = 0; i < ids.length; i++) {
                uf = this.get(ids[i]);
                System.out.println(uf);
                if (UserContext.getCurrent().getId().equals(uf.getApplier().getId())) {
                    fileType = new SystemDictionaryItem();
                    fileType.setId(fileTypes[i]);
                    uf.setFileType(fileType);
                    this.update(uf);
                }
            }
        }
    }

    @Override
    public List<UserFile> selectFileTypeByCondition(boolean isSelectFileType) {
        return userFileMapper.selectFileTypeByCondition(UserContext.getCurrent().getId(), isSelectFileType);
    }

    @Override
    public PageResult queryPage(UserFileQueryObject qo) {
        int total = userFileMapper.queryForCount(qo);
        List<UserFile>list = userFileMapper.queryForList(qo);

        return new PageResult(total,list);
    }

    @Override
    public void audit(Long id, int state, int score, String remark) {
        //根据id获取风控材料对象,处于待审核状态
        UserFile userFile = this.get(id);
        if (userFile != null && userFile.getState() == UserFile.STATE_NORMAL) {
            userFile.setAuditor(UserContext.getCurrent());
            userFile.setAuditTime(new Date());
            userFile.setRemark(remark);

            if (state == UserFile.STATE_PASS) {
                userFile.setState(UserFile.STATE_PASS);
                userFile.setScore(score);
                Userinfo userinfo = userinfoService.get(userFile.getApplier().getId());
                userinfo.setScore(userinfo.getScore() + score);
                userinfoService.update(userinfo);
            } else {
                userFile.setState(UserFile.STATE_REJECT);
            }
            this.update(userFile);
        }
    }

    @Override
    public List<UserFile> queryBuUserId(Long userId) {

        return userFileMapper.queryByUserId(userId,UserFile.STATE_PASS);
    }
}
