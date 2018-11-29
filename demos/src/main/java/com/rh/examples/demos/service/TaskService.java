package com.rh.examples.demos.service;

import com.rh.examples.demos.utils.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * description:
 * author: Ruh
 * time: 2018/11/29.
 */
@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public ResponseMsg<String> getResult(){

        log.info("{} 任务开始执行，持续等待中...", Thread.currentThread().getName());

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("{} 任务处理完成", Thread.currentThread().getName());
        return new ResponseMsg<>(0,"操作成功","success");
    }
    // //blog.csdn.net/m0_37595562/article/details/81013909
}
