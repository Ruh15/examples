package com.rh.example.rabbitmq;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender implements CommandLineRunner {

    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = (CorrelationData correlationData, boolean ack, String cause) -> {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        if (!ack) {
            System.err.println("异常处理....");
        }
    };

    //回调函数: return返回
    final RabbitTemplate.ReturnCallback returnCallback = (
            org.springframework.amqp.core.Message message, int replyCode,
            String replyText, String exchange, String routingKey) -> {
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
    };

    //发送消息方法调用: 构建Message消息
    public void send(Object message, Map<String, Object> properties) throws Exception {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message<Object> msg = MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend("rh", "springboot.abc", msg, correlationData);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        send("rh", null);
    }

//    //发送消息方法调用: 构建自定义对象消息
//    public void sendOrder(Order order) throws Exception {
//        rabbitTemplate.setConfirmCallback(confirmCallback);
//        rabbitTemplate.setReturnCallback(returnCallback);
//        //id + 时间戳 全局唯一
//        String id = UUID.randomUUID().toString();
//        log.info("sendOrder id: {}", id);
//        CorrelationData correlationData = new CorrelationData(id);
//        rabbitTemplate.convertAndSend("exchange-2", "springboot.def", order, correlationData);
//    }

}
