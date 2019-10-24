package com.rh.examples.demos;

import java.util.Scanner;

/*
 * 注册类
 * 1.提示用户输入账号密码
 * 2.产生随机号[1000,2000]
 * 3.保存用户注册信息
 * 4.提示注册成功
 */
public class Register {
	// 注册方法
	public static void userRegister() {
		System.out.println("请填写个人信息:");
		System.out.println("用户名:");
		Scanner scanner = new Scanner(System.in);
		// 接收用户信息
		String userName = scanner.nextLine();
		System.out.println("密码:");
		// 接收密码
		String password = scanner.nextLine();
		// 随机生成卡号
		int cardNumber = (int)(Math.random() * (2000 - 1000 + 1) + 1000);
		// 保存到用户类中
		User.userName = userName;
		User.password = password;
		User.cardNumber = cardNumber;
		// 提示注册成功
		System.out.println("注册成功,请记好你的会员卡号");
		System.out.println("用户名:" + User.userName);
		System.out.println("密码:" + User.password);
		System.out.println("卡号:" + User.cardNumber);
		// 保存注册状态
		User.isRegister = true;
		// 整个工程,如果使用多次 scanner 只能关闭一次
		// scanner.close();
	}
}