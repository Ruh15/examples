package com.rh.examples.demos.config;

import com.rh.examples.demos.utils.ResponseMsg;
import com.rh.examples.demos.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * description: 任务队列
 * 对queue的操作，分别用了offer和poll，这样是实现一个非阻塞的操作，并且在队列为空和队列已满的情况下不会抛出异常
 * 可以考虑使用ConcurrentLinkedQueue来高效处理并发，这里选择阻塞队列，在使用的时候需要加锁。
 * author: Ruh
 * time: 2018/11/29.
 */
@Component
public class TaskQueue {

    private static final Logger log = LoggerFactory.getLogger(TaskQueue.class);

    private static final int QUEUE_LENGTH = 10;

    private BlockingQueue<Task> queue = new LinkedBlockingDeque<>(QUEUE_LENGTH);

    private int taskId = 0;

    /**
     * 加入任务
     * @param deferredResult
     */
    public void put(DeferredResult<ResponseMsg<String>> deferredResult) {
        taskId ++;
        log.info("{}, 任务加入队列，id 为 {}", Thread.currentThread().getName(), taskId );
        queue.offer(new Task(taskId, deferredResult));
    }

    /**
     * 获取任务
     * @return {@link Task}
     */
    public Task take() {
        Task task = queue.poll();
        log.info("获得任务：{}", task);
        return task;
    }
}
