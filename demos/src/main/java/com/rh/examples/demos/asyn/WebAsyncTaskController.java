package com.rh.examples.demos.asyn;

import com.rh.examples.demos.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * description: 通过WebAsyncTask，实现异步调用
 * author: Ruh
 * time: 2018/11/29.
 */
@RestController
@RequestMapping("/web")
public class WebAsyncTaskController {

    private static final Logger logger = LoggerFactory.getLogger(WebAsyncTaskController.class);

    @Autowired
    private HelloService helloService;

    @GetMapping("/world")
    public WebAsyncTask<String> world() {

        logger.info("{} 进入 world 方法", Thread.currentThread().getName());

        // 设置 3s 超时
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(3000, new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("{} 进入call方法", Thread.currentThread().getName());
                String res = helloService.sayHello();
                logger.info("{} 从helloService方法返回", Thread.currentThread().getName());
                return res;
            }
        });

        logger.info("{} 从 world 方法返回", Thread.currentThread().getName());

        webAsyncTask.onCompletion(new Runnable() {
            @Override
            public void run() {
                logger.info("{} 执行完毕", Thread.currentThread().getName());
            }
        });

        webAsyncTask.onTimeout(() -> {
            logger.info("{} onTimeout", Thread.currentThread().getName());
            // 超时的时候，直接抛异常，让外层统一处理超时异常
            throw new TimeoutException("调用超时");
        });
        return webAsyncTask;
    }

    /**
     * 异步调用，异常处理，详细的处理流程见 MyExceptionHandler 类
     *
     * @return
     */
    @GetMapping("/exception")
    public WebAsyncTask<String> exceptionController() {
        logger.info("{} 进入 exceptionController 方法", Thread.currentThread().getName());
        Callable<String> callable = () -> {
            logger.info("{} 进入 call 方法", Thread.currentThread().getName());
            throw new TimeoutException("调用超时!");
        };
        logger.info("{} 从 exceptionController 方法返回", Thread.currentThread().getName());
        return new WebAsyncTask<>(20000, callable);
    }

}
