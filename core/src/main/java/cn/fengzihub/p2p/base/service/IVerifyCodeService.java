package cn.fengzihub.p2p.base.service;

/**
 * Created by Administrator on 2018.02.10.
 */
public interface IVerifyCodeService {
    /**
     * 发送短信
     * @param phoneNumber
     */
    void sendVerifyCode(String phoneNumber);

    /**
     * 校验验证码
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    boolean validate(String phoneNumber, String verifyCode);
}
