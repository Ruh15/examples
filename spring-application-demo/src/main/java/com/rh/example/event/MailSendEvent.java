package com.rh.example.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by ruhui on 2019/8/24.
 * content: 继承了ApplicationContextEvent，就是个容器事件
 */
public class MailSendEvent extends ApplicationContextEvent{

    private String sendTo;  //收件人

    public MailSendEvent(ApplicationContext source) {
        super(source);
    }

    public MailSendEvent(ApplicationContext source, String sendTo) {
        super(source);
        this.sendTo = sendTo;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
