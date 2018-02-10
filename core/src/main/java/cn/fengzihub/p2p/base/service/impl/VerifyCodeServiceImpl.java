package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.service.IVerifyCodeService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.DateUtil;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.base.vo.VerifyCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2018.02.10.
 */
@Service
@Transactional
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Override
    public void sendVerifyCode(String phoneNumber) {

        //判断用户之前是否有发送短信验证码,及上一次发送短信有没有超过5分钟
        VerifyCode vo = UserContext.getVerifyCode();
        if (vo == null || DateUtil.getBetweenTime(vo.getSendTime(), new Date()) > BidConst.MANAGER_INTERVAL_TIME) {
            //1.生成验证码
            String uuid = UUID.randomUUID().toString().substring(0, 4);
            //2.拼接发送短信的内容
            StringBuilder msg = new StringBuilder(50);
            msg.append("这是你的验证码").append(uuid).
                    append(",有效期为").append(BidConst.MESSAGE_VALID_TIME).append("分钟,请尽快使用");
            System.out.println("========="+msg);
            //3.执行短信发送,模拟短信发送

            VerifyCode verifyCode = new VerifyCode();
            verifyCode.setPhoneNumber(phoneNumber);
            verifyCode.setVerifyCode(uuid);
            verifyCode.setSendTime(new Date());

            //把对象放入到session中
            UserContext.setVerifyCode(verifyCode);
        } else {
            throw new RuntimeException("操作太频繁");
        }



    }

    @Override
    public boolean validate(String phoneNumber, String verifyCode) {

        //从session中获取验证码
        VerifyCode verifyCodeVo = UserContext.getVerifyCode();
        if (verifyCodeVo != null &&
                verifyCodeVo.getVerifyCode().equalsIgnoreCase(verifyCode) &&
                verifyCodeVo.getPhoneNumber().equals(phoneNumber) &&
                DateUtil.getBetweenTime(verifyCodeVo.getSendTime(), new Date())<=BidConst.MESSAGE_VALID_TIME*60) {
            return true;
        }
        return false;
    }
}
