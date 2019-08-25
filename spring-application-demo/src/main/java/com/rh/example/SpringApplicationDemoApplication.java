package com.rh.example;

import com.rh.example.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplicationDemoApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringApplicationDemoApplication.class, args);
//		new SpringApplicationBuilder(SpringApplicationDemoApplication.class)
//				.listeners(event -> { // 添加监听器
//					System.out.println("监听到事件" + event.getClass().getName());
//				})
//				.run(args);// 运行

		MailSendService sender = applicationContext.getBean(MailSendService.class);
		sender.sendEmail("北京");

	}

}
