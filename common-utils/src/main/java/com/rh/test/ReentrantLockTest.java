package com.rh.test;


import org.junit.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReentrantLock;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/8/9
 **/
public class ReentrantLockTest {
    private  ReentrantLock lock = new ReentrantLock(true);

    @Test
    public void test() throws IOException {
        new Thread(()->print(), "1").start();
        Thread thread = new Thread(() -> print(), "2");
        thread.start();
        thread.interrupt();
        System.in.read();
    }

    @Test
    public void testAssert() {
        int x = 12;
        Assert.isTrue(x <= 36 && x >= 12, "error");
    }

    @Test
    public void testBig() {
        BigDecimal x = new BigDecimal(22);
        BigDecimal y = new BigDecimal(35);
        BigDecimal z = new BigDecimal(0.7);
        System.out.println(y.multiply(z).setScale(2, RoundingMode.DOWN));
        System.out.println(x.subtract(y.multiply(z)).setScale(2, RoundingMode.DOWN));
    }

    private void print() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "start");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "end");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "Interrupted");
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
