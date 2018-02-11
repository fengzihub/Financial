package cn.fengzihub.p2p.base.service.impl;

import cn.fengzihub.p2p.base.domain.MailVerify;
import cn.fengzihub.p2p.base.service.IEmailService;
import cn.fengzihub.p2p.base.service.IMailVerifyService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2018.02.11.
 */
@Service@Transactional
public class EmailServiceImpl implements IEmailService {
    @Value("${email.applicationUrl}")
    private String applicationUrl;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private IMailVerifyService mailVerifyService;
    @Override
    public void sendEmail(String email) {
        //拼接认证信息内容
        String emailUUID = UUID.randomUUID().toString();
        StringBuilder msg = new StringBuilder();
        msg.append("这是你的邮箱验证码,点击<a href='").append(applicationUrl).
                append("/bindEmail?key=").append(emailUUID).append("'>这里</a>完成你认证,有效期为").
                append(BidConst.EAMIL_VAILD_TIME).append("天,请尽快认证");

        try {
            MailVerify mailVerify = new MailVerify();
            mailVerify.setSendTime(new Date());
            mailVerify.setEmail(email);
            mailVerify.setUserId(UserContext.getCurrent().getId());
            mailVerify.setUuid(emailUUID);
            mailVerifyService.save(mailVerify);
            sendEmail(email, msg.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }
    //SpringBoot集成发送邮件
    public void sendEmail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("这是一封认证邮件!!!");
        helper.setTo(email);
        helper.setFrom(fromEmail);
        helper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }
}
