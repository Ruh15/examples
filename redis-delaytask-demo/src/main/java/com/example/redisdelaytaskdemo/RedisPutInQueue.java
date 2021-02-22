package com.example.redisdelaytaskdemo;

import org.redisson.config.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import example.redisdelaytaskdemo.User;


/**
 * @author ruhui
 */
public class RedisPutInQueue {
    public static void main(String args[]) {
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.1.204:6379").setDatabase(2);
        RedissonClient redissonClient = Redisson.create(config);
        RBlockingQueue<User> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");

        RDelayedQueue<User> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            // 一分钟以后将消息发送到指定队列
            //相当于1分钟后取消订单
            //  延迟队列包含 user 1分钟，然后将其传输到blockingFairQueue中
            //在1分钟后就可以在blockingFairQueue 中获取 user
            User user = new User();
            user.setAge(30);
            user.setPutTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
            delayedQueue.offer(user, 10, TimeUnit.SECONDS);

        }
        //         在该对象不再需要的情况下，应该主动销毁。
        // 仅在相关的Redisson对象也需要关闭的时候可以不用主动销毁。
        delayedQueue.destroy();

        //redissonClient.shutdown();
    }
}
