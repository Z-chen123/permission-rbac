package com.mmall.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;
public class EmailUtil {
    /**
     * 发送邮件
     * @param to 收件人
     * @param text 邮件文本内容
     * @param title 邮件标题
     * @return
     */
    public static Boolean sendEmial(String to, String text, String title) {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");
        try {
            // 开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            // 创建session
            Session session = Session.getInstance(prop);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，16位授权码
            ts.connect("smtp.qq.com", "2605410440@qq.com", "zddzjrnnxvyjdief");
            // 创建邮件
            Message message = createSimpleMail(session, to, text, title);
            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            //发送失败
            return false;
        }
        return true;
    }

    /**
     *
     * @param session
     * @param to 收件人
     * @param text 邮件文本内容
     * @param title 邮件标题
     * @return
     * @throws Exception
     */
    public static MimeMessage createSimpleMail(Session session, String to, String text, String title) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("2605410440@qq.com"));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 邮件的标题
        message.setSubject(title);
        // 邮件的文本内容
        message.setContent(text, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }

    public static void main(String[] args) {
        //测试
        EmailUtil.sendEmial("2605410440@qq.com", "你好，这是一封登录权限邮件，密码：123456, 无需回复。", "测试邮件");
    }
}
