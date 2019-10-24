package com.rh.examples.demos;

import java.util.Scanner;

/*
 * 登录类
 * 1.输入登录的账号和密码
 * 2.和用户信息进行匹配
 *   有三次重新输入的机会
 * 3.登录成功
 */
public class Login {
	// 保存登录失败的次数
	static int num = 0;
	// 登录方法
	// 静态方法里面需要使用静态成员变量
	public static boolean userLogin() {
		// 先判断是否注册
		if (User.isRegister == false) {
			System.out.println("请先进行注册!");
			Register.userRegister();
		} else {
			// 输入信息
			System.out.println("请填写个人信息:");
			System.out.println("用户名:");
			Scanner scanner = new Scanner(System.in);
			// 接收信息
			String userName = scanner.nextLine();
			System.out.println("密码:");
			String password = scanner.nextLine();
			// 匹配登录信息
			if (userName.equals(User.userName) && password.equals(User.password)) {
				// 登录成功
				System.out.println("欢迎您:" + userName);
				// 返回登录结果
				return true;
			} else {
				// 登录失败
				num++;
				System.out.println("用户名和密码有误,请重新输入!");
				System.out.println("还剩:" + (3 - num) + "次机会");
				// 判断输入登录信息错误几次
				if (num < 3) {
					// 继续登录
					userLogin();
				} else if (num == 3) {
					// 登录失败
					System.out.println("三次机会已用完,请明天重试!");
					// 重置记录登录次数的变量
					num = 0;
				}
			}
		}
		// 代码执行到这,说明登录一定失败了
		return false;
	}
}