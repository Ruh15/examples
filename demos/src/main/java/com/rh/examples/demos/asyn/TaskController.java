package com.rh.examples.demos.asyn;

import com.rh.examples.demos.config.TaskQueue;
import com.rh.examples.demos.utils.ResponseMsg;
import com.rh.examples.demos.utils.TaskSet;
import com.rh.examples.demos.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * description:
 * author: Ruh
 * time: 2018/11/29.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    //超时结果
    private static final ResponseMsg<String> OUT_OF_TIME_RESULT = new ResponseMsg<>(-1,"超时","out of time");

    //超时时间
    private static final long OUT_OF_TIME = 3000L;

    @Autowired
    private TaskQueue taskQueue;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Callable<ResponseMsg<String>> getResult(){

        log.info("{} 接收请求，开始处理...", Thread.currentThread().getName());
        Callable<ResponseMsg<String>> result = (() -> {
            return taskService.getResult();
        });

        log.info("{} 接收任务线程完成并退出", Thread.currentThread().getName());

        return result;
    }

    @RequestMapping(value = "/getDeffer",method = RequestMethod.GET)
    public DeferredResult<ResponseMsg<String>> getDefferResult() {
        log.info("{} 接收请求，开始处理...", Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        //建立DeferredResult对象，设置超时时间，以及超时返回超时结果
        DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);
        result.onTimeout(() -> {
            log.info("{} 调用超时--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        });
        result.onCompletion(() -> {
            log.info("{} 调用完成--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        });
        //并发，加锁
        synchronized (taskQueue) {
            taskQueue.put(result);
        }

        log.info("{} 接收任务线程完成并退出--> 耗时:{}ms", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        return result;

    }

    /*   ---------------------------------------------------     */
    @Autowired
    private TaskSet taskSet;

    @RequestMapping(value = "/getDeffer2",method = RequestMethod.GET)
    public DeferredResult<ResponseMsg<String>> getDefferResult2(){

        log.info("接收请求，开始处理...");

        //建立DeferredResult对象，设置超时时间，以及超时返回超时结果
        DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(OUT_OF_TIME, OUT_OF_TIME_RESULT);

        result.onTimeout(() -> {
            log.info("调用超时，移除任务，此时队列长度为{}", taskSet.getSet().size());
            synchronized (taskSet.getSet()) {
                taskSet.getSet().remove(result);
            }
        });

        result.onCompletion(() -> {
            log.info("调用完成，移除任务，此时队列长度为{}",taskSet.getSet().size());
            synchronized (taskSet.getSet()) {
                taskSet.getSet().remove(result);
            }
        });

        //并发，加锁
        synchronized (taskSet.getSet()) {
            taskSet.getSet().add(result);
        }
        log.info("加入任务集合，集合大小为:{}", taskSet.getSet().size());
        log.info("接收任务线程完成并退出");
        return result;

    }
//    原文：https://blog.csdn.net/m0_37595562/article/details/81013909
}
