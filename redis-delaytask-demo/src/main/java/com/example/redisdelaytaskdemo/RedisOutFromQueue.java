package com.example.redisdelaytaskdemo;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class RedisOutFromQueue {
    public static void main(String args[]) {
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.1.204:6379").setDatabase(2);
        RedissonClient redissonClient = Redisson.create(config);
        RBlockingQueue<User> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");
        RDelayedQueue<User> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        while (true) {
            User user = null;
            try {
                user = blockingFairQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订单取消时间：" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "==订单生成时间" + user.getPutTime());
        }
    }
}
