package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.MailVerify;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.mapper.UserinfoMapper;
import cn.fengzihub.p2p.base.service.IMailVerifyService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.service.IVerifyCodeService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.DateUtil;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2018.02.10.
 */
@Service@Transactional
public class UserinfoServiceImpl implements IUserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private IVerifyCodeService verifyCodeService;
    @Autowired
    private IMailVerifyService mailVerifyService;
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

    @Override
    public void bindEmail(String key) {
        //根据用户ip地址key =UUID去数据库中查询用户绑定记录
        MailVerify mailVerify = mailVerifyService.get(key);
        if (mailVerify == null) {
            throw new RuntimeException("验证码邮箱地址有误,请重新验证");
        }
        //判断邮箱验证时间是否在有效时间内
        if (DateUtil.getBetweenTime(mailVerify.getSendTime(), new Date()) > BidConst.EAMIL_VAILD_TIME * 60 * 60 * 24) {
            throw new RuntimeException("验证码已过期,请重新验证");
        }
        //因为不在同一平台上认证,所以不能把认证信息放到session中
        //只能从数据库中获取userinfo是否有绑定邮箱
        Userinfo userinfo = this.get(mailVerify.getUserId());
        if (userinfo.getHasBindEmail()) {
            throw new RuntimeException("你已经绑定邮箱了,请不要重复绑定");
        }
        //运行到这里说明绑定没问题
        //设置绑定邮箱状态
        userinfo.addSate(BitStatesUtils.OP_BIND_EMAIL);

        userinfo.setEmail(mailVerify.getEmail());
        this.update(userinfo);
    }
}
