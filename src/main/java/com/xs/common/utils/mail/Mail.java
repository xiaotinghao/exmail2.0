package com.xs.common.utils.mail;

/**
 * @Description:邮件信息类
 *
 * @ClassName: SimpleMail
 */
public class Mail {
    /**
     * 发送人
     */
    private String sender;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private Object content;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
