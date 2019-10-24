package com.rh.example.listener;

import com.rh.example.event.MailSendEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * MailSendEvent 事件监听器
 */
@Component
public class MailSendListener implements ApplicationListener<MailSendEvent> {
    @Override
    public void onApplicationEvent(MailSendEvent mailSendEvent) {
        MailSendEvent event = mailSendEvent;
        System.out.println("当前线程：" + Thread.currentThread().getName());
        System.out.println("MailSender向"+ event.getSendTo()+ "发送了邮件");
    }
}