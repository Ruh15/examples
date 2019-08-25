package com.rh.example.service;

import com.rh.example.event.MailSendEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by ruhui on 2019/8/24.
 * content: 发送邮件服务
 */
@Service
public class MailSendService {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发送邮件
     * @param sendTo
     */
    public void sendEmail(String sendTo) {
        System.out.println("开始发送邮件");
        System.out.println("当前线程：" + Thread.currentThread().getName());
        MailSendEvent mailSendEvent = new MailSendEvent(applicationContext, sendTo);
        applicationContext.publishEvent(mailSendEvent);
    }
}
