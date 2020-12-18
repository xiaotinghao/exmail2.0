package com.xs.common.utils.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.xs.common.utils.mail.MailConstants.MAIL_PWD;
import static com.xs.common.utils.mail.MailConstants.MAIL_USER;

/**
 * 邮件发送测试类
 *
 * @author 18871430207@163.com
 */
public class MailSendTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        // 需要在QQ邮箱设置中开启POP3服务
        MailSender mailSender = new MailSender(MAIL_USER, MAIL_PWD);

        StringBuilder content = new StringBuilder();
        content.append("<div style='text-indent:1em;'>");
        content.append("尊敬的蔡恩庭先生，您好！");
        content.append("</div>");
        content.append("<br>");
        content.append("<div style='color:blue;text-indent:1em;'>");
        content.append("诚邀您参加我司2020-11-18上午10点的面试，");
        content.append("面试地址：湖北省武汉市江夏区腾讯大道01号，期待您的到来！");
        content.append("</div>");

        Vector<File> files = new Vector<>();

        List<String> recipients = new ArrayList<>();
        recipients.add("18871430207@163.com");
        Mail mail = new Mail();
        mail.setSender("腾讯科技");
        mail.setSubject("面试邀请函");
        mail.setContent(content);

        try {
            mailSender.send(recipients, mail, files);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
