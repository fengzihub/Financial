package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.mapper.UserinfoMapper;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.service.IVerifyCodeService;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.10.
 */
@Service@Transactional
public class UserinfoServiceImpl implements IUserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private IVerifyCodeService verifyCodeService;

    @Override
    public int save(Userinfo userinfo) {
        return userinfoMapper.insert(userinfo);
    }

    @Override
    public int update(Userinfo userinfo) {
        int count = userinfoMapper.updateByPrimaryKey(userinfo);
        if (count <= 0) {
            throw new RuntimeException("乐观锁异常" + userinfo.getId());
        }
        return count;
    }

    @Override
    public Userinfo get(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Userinfo getCurrent() {
        return this.get(UserContext.getCurrent().getId());
    }

    @Override
    public void bindPhone(String phoneNumber, String verifyCode) {
        //验证码是否有效,手机号码是否是之前发送短信的号码
        boolean isVaild = verifyCodeService.validate(phoneNumber, verifyCode);
        if (!isVaild) {
            throw new RuntimeException("验证码有误,请重新发送");
        }
        //判断用户是否已经绑定了手机号码
        Userinfo userinfo = this.getCurrent();
        if (userinfo.getHasBindPhone()) {
            throw new RuntimeException("你已经绑定了手机,不要重复绑定");
        }

        //给用户的userinfo添加手机认证的状态码
        userinfo.addSate(BitStatesUtils.OP_BIND_PHONE);
        userinfo.setPhoneNumber(phoneNumber);
        this.update(userinfo);
    }

}
