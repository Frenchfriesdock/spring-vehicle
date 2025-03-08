package com.hosiky.common.client;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailClient {

    private final MailSender mailSender;

    public void sendMail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hosiky@126.com");
        message.setTo(to);
        message.setSubject("一次性代码测试");
        message.setText(createText(to, code));
        mailSender.send(message);
    }

    private String createText(String to, String code) {
        // 构建一个友好的邮件内容
        return to + ",\n\n" + // 添加收件人问候







                "您好，\n" +
                "感谢您使用我们的服务。\n" +
                "您的验证码是：\n" +
                code + "\n\n" +
                "请勿将此验证码透露给任何人或第三方，否则可能会导致您的账号安全问题。\n\n" +
                "如果您并未申请验证码，请忽略此邮件。\n\n" +
                "谢谢!";
    }
}
