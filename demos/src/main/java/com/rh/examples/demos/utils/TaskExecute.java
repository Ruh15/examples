package com.rh.examples.demos.utils;

import com.rh.examples.demos.config.TaskQueue;
import com.rh.examples.demos.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * 这里，我们注入了TaskQueue，成员变量比较简单并且有注释，不再说明，主要来看方法。
 * 先看一下最后一个方法getRandomStr，很显然，这是一个获得长度为len的随机串的方法，访问限定为private，为类中其他方法服务的。
 * 然后我们看init方法，它执行的其实就是开启了一个线程并且执行execute方法，注意一下它上面的@PostContruct注解，
 * 这个注解就是在这个bean初始化的时候就执行这个方法。所以我们需要关注的实际逻辑在execute方法中。
 * 可以看到，在execute方法中，用了一个while(true)来保证线程持续运行。
 * 因为是并发环境下，考虑对taskQueue加锁，从中取出任务；如果任务不为空，获取用getRandomStr生成一个随机结果并用setResult方法进行返回。
 * 最后可以看到，利用random生成来一个[0,10)的随机数，并让线程sleep相应的秒数。
 * 这里注意一下，需要设定一个时间间隔，否则，先线程持续跑会出现CPU负载过高的情况。
 * 接下来我们就看看controller是如何处理的
 */
@Component
public class TaskExecute {

    private static final Logger log = LoggerFactory.getLogger(TaskExecute.class);

    private static final Random random = new Random();

    //默认随机结果的长度
    private static final int DEFAULT_STR_LEN = 10;

    //用于生成随机结果
    private static final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    private TaskQueue taskQueue;

    /**
     * 初始化启动
     */
    @PostConstruct
    public void init() {
        log.info("开始持续处理任务");
        new Thread(this::execute).start();
    }

    /**
     * 持续处理
     * 返回执行结果
     */
    private void execute() {
        while (1==1) {
            try {
                //取出任务
                Task task;
                synchronized (taskQueue) {
                    task = taskQueue.take();
                }
                if (task != null) {
                    //设置返回结果
                    String randomStr = getRandomStr(DEFAULT_STR_LEN);
                    ResponseMsg<String> responseMsg = new ResponseMsg<>(0, "success", randomStr);
                    log.info("返回结果:{}", responseMsg);
                    task.getTaskResult().setResult(responseMsg);
                }
                int time = random.nextInt(10);
                log.info("处理间隔：{}秒", time);
                Thread.sleep(time * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取长度为len的随机串
     *
     * @param len
     * @return
     */
    private String getRandomStr(int len) {
        int maxInd = str.length();
        StringBuilder sb = new StringBuilder();
        int ind;
        for (int i = 0; i < len; i++) {
            ind = random.nextInt(maxInd);
            sb.append(str.charAt(ind));
        }
        return String.valueOf(sb);
    }
}
//原文：https://blog.csdn.net/m0_37595562/article/details/81013909