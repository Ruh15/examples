package com.rh.examples.demos.asyn;

import com.rh.examples.demos.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * description: 测试异步请求
 *  参考了 https://download.csdn.net/download/xiangxizhishi/9886892
 *  https://blog.csdn.net/liuchuanhong1/article/details/78744138
 * author: Ruh
 * time: 2018/11/26.
 */
@RestController
public class AsynController {
    private static final Logger logger = LoggerFactory.getLogger(AsynController.class);

    @Autowired
    private HelloService helloService;

    @GetMapping("/helloworld")
    public String helloWorldController() {
        long start = System.currentTimeMillis();
        String res = helloService.sayHello();
        long timeConsuming = System.currentTimeMillis() - start;
        logger.info("{} 从helloController方法返回， 用时{}", Thread.currentThread().getName(), timeConsuming);
        return res;
    }

    /**
     * 异步调用restful
     * 当controller返回值是Callable的时候，springmvc就会启动一个线程将Callable交给TaskExecutor去处理
     * 然后DispatcherServlet还有所有的spring拦截器都退出主线程，然后把response保持打开的状态
     * 当Callable执行结束之后，springmvc就会重新启动分配一个request请求，然后DispatcherServlet就重新
     * 调用和处理Callable异步执行的返回结果， 然后返回视图
     *
     * @return
     */
    @GetMapping("/hello")
    public Callable<String> helloController() {
        long start = System.currentTimeMillis();
        logger.info("{}进入helloController方法", Thread.currentThread().getName());
        Callable<String> callable = new Callable<String>() {
            public String call() {
                logger.info("{} 进入call方法", Thread.currentThread().getName());
                String say = helloService.sayHello();
                logger.info("{} 从helloService方法返回", Thread.currentThread().getName());
                return say;
            }
        };
        long timeConsuming = System.currentTimeMillis() - start;
        logger.info("{} 从helloController方法返回， 用时{}", Thread.currentThread().getName(), timeConsuming);
        return callable;
    }


    // 输出结果
//    http-nio-8080-exec-2 从helloController方法返回， 用时201
//    http-nio-8080-exec-3进入helloController方法
//    http-nio-8080-exec-3 从helloController方法返回， 用时1
//    task-1 进入call方法
//    task-1 从helloService方法返回
}
