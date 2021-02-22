package com.example.dateformat;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateUtilTest {

	public static void main(String[] args) throws InterruptedException {

		MyThread t1 = new MyThread(new Date(), "yyyy-MM-dd");

		MyThread t2 = new MyThread(new Date(), "yyyy-MM-dd");

		MyThread t3 = new MyThread(new Date(), "yyyy-MM-dd");

		MyThread t4 = new MyThread("2017-06-10 12:00:01", "yyyy-MM-dd HH:mm:ss");

		MyThread t5 = new MyThread("2017-06-10 12:00:01", "yyyy-MM-dd HH:mm:ss");

		System.out.println("单线程执行：");
		ExecutorService exec1 = Executors.newFixedThreadPool(1);
		exec1.execute(t1);
		exec1.execute(t2);
		exec1.execute(t3);
		exec1.execute(t4);
		exec1.execute(t5);
		exec1.shutdown();

		Thread.sleep(1000);
		System.out.println("双线程执行：");
		ExecutorService exec2 = Executors.newFixedThreadPool(2);
		exec2.execute(t1);
		exec2.execute(t2);
		exec2.execute(t3);
		exec2.execute(t4);
		exec2.execute(t5);
		exec2.shutdown();
	}
}
