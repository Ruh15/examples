package com.rh.examples.demos.service;

import com.rh.examples.demos.utils.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

/**
 * description:
 * author: Ruh
 * time: 2018/11/29.
 */
@Component
public class LongTimeTask {
    private static final Logger logger = LoggerFactory.getLogger(LongTimeTask.class);

    @Async
    public void execute(DeferredResult<String> deferred){
        logger.info("{} 进入 taskService 的 execute方法", Thread.currentThread().getName());
        try {
            // 模拟长时间任务调用，睡眠2s
            TimeUnit.SECONDS.sleep(2);
            // 2s后给Deferred发送成功消息，告诉Deferred，我这边已经处理完了，可以返回给客户端了
            deferred.setResult("world");
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Async
    public void execute2(DeferredResult<ResponseMsg<String>> deferred) {
        logger.info("{} 进入 taskService 的 execute方法", Thread.currentThread().getName());
        try {
            // 模拟长时间任务调用，睡眠2s
            TimeUnit.SECONDS.sleep(2);
            // 2s后给Deferred发送成功消息，告诉Deferred，我这边已经处理完了，可以返回给客户端了
            deferred.setResult(new ResponseMsg<>(0,"处理完成","complete"));
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
