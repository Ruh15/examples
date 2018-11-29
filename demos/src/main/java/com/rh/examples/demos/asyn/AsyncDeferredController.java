package com.rh.examples.demos.asyn;

import com.rh.examples.demos.config.TaskQueue;
import com.rh.examples.demos.utils.ResponseMsg;
import com.rh.examples.demos.service.LongTimeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * description: Deferred方式实现异步调用
 * author: Ruh
 * time: 2018/11/29.
 */
@RestController
public class AsyncDeferredController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LongTimeTask longTimeTask;

    //超时结果
    private static final ResponseMsg<String> OUT_OF_TIME_RESULT = new ResponseMsg<>(-1,"超时","out of time");

    //超时时间
    private static final long OUT_OF_TIME = 1000L;

    @Autowired
    public AsyncDeferredController(LongTimeTask taskService) {
        this.longTimeTask = taskService;
    }

    @Autowired
    private TaskQueue taskQueue;

    @GetMapping("/deferred")
    public DeferredResult<String> executeSlowTask() {
        logger.info("{} 进入executeSlowTask方法", Thread.currentThread().getName());
        long start = System.currentTimeMillis();

        DeferredResult<String> deferredResult = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);
        // 调用长时间执行任务
        longTimeTask.execute(deferredResult);
        // 当长时间任务中使用deferred.setResult("world");这个方法时，会从长时间任务中返回，继续controller里面的流程
        logger.info("{} 从executeSlowTask方法返回", Thread.currentThread().getName());

        // 超时的回调方法
        deferredResult.onTimeout(() ->{
            logger.info("{} 调用超时--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
            // 返回超时信息
            deferredResult.setErrorResult("time out!");
        });

        // 处理完成的回调方法，无论是超时还是处理成功，都会进入这个回调方法
        deferredResult.onCompletion(() -> logger.info("{} onCompletion--> 耗时:{}ms",
                Thread.currentThread().getName(), System.currentTimeMillis() - start));
        return deferredResult;
    }

    @RequestMapping(value = "/deferred2",method = RequestMethod.GET)
    public DeferredResult<ResponseMsg<String>> getDefferResult() {
        logger.info("{} 接收请求，开始处理...", Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        //建立DeferredResult对象，设置超时时间，以及超时返回超时结果
        DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);
        result.onTimeout(() -> {
            logger.info("{} 调用超时--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        });
        result.onCompletion(() -> {
            logger.info("{} 调用完成--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        });
        DeferredResult<String> deferredResult = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);
        // 调用长时间执行任务
        longTimeTask.execute(deferredResult);
//        //并发，加锁
//        synchronized (taskQueue) {
//            taskQueue.put(result);
//        }

        logger.info("{} 接收任务线程完成并退出--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        return result;

    }
}
