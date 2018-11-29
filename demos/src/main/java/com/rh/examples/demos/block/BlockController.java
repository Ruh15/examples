package com.rh.examples.demos.block;

import com.rh.examples.demos.utils.ResponseMsg;
import com.rh.examples.demos.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: 阻塞调用
 * author: Ruh
 * time: 2018/11/29.
 */
@RestController
@RequestMapping("/block")
public class BlockController {

    private static final Logger log = LoggerFactory.getLogger(BlockController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping("/get")
    public ResponseMsg<String> getResult(){
        log.info("{} 接收请求，开始处理...", Thread.currentThread().getName());
        ResponseMsg<String> result =  taskService.getResult();
        log.info("{} 接收任务线程完成并退出", Thread.currentThread().getName());
        return result;
    }
}
