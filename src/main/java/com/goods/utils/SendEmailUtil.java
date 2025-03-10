package com.goods.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtil {

    @Resource
    private static JavaMailSender javaMailSender;

    public static void sendMailCode(String toEmail, String code)  {

        try {
            // 创建Properties 类用于记录邮箱的一些属性
            Properties props = new Properties();
            // 表示SMTP发送邮件，必须进行身份验证
            props.put("mail.smtp.auth", "true");
            //此处填写SMTP服务器
            props.put("mail.smtp.host", "smtp.qq.com");
            //端口号，QQ邮箱端口587
            props.put("mail.smtp.port", "465");
            // 此处填写，写信人的账号
            props.put("mail.user", "2220747894@qq.com");
            // 此处填写16位STMP口令
            props.put("mail.password", "sbydphirvfusdhhi");

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("2220747894@qq.com");
                    String password = props.getProperty("vytgnsofhqyodjda");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress("2220747894@qq.com");
            message.setFrom(form);

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(toEmail);
            message.setRecipient(Message.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject("标题");

            // 设置邮件的内容体
            message.setContent("【验证码:】"+code+",有效期5min。", "text/html;charset=UTF-8");

            // 最后当然就是发送邮件啦
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
