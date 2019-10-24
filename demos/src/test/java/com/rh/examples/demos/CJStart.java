package com.rh.examples.demos;

import java.util.Scanner;

/*
 * 抽奖开始类(组装抽奖逻辑)
 */
public class CJStart {
	// 抽奖开始
	public void cjStart() {
		// 是否继续菜单
		String isGoOn = "";
		// 循环主菜单
		do {
			System.out.println("*******欢迎进入抽奖系统*******");
			System.out.println("         1.注册");
			System.out.println("         2.登录");
			System.out.println("         3.抽奖");
			System.out.println("*****************************");
			System.out.println("请选择菜单:");
			Scanner scanner = new Scanner(System.in);
			// 接收菜单选项
			String menuNum = scanner.nextLine();
			// 判断选项
			chooseMenuNum(menuNum);
			// 接收是否继续
			System.out.println("是否选择继续?y/n");
			isGoOn = scanner.nextLine();
			while(!isGoOn.equals("y") && !isGoOn.equals("n")) {
				System.out.println("输入信息有误,请重新输入!");
				isGoOn = scanner.nextLine();
			}
		} while (isGoOn.equals("y"));
	}
	// 判断菜单选项
	public void chooseMenuNum(String menuNum) {
		switch (menuNum) {
		case "1":
			System.out.println("[抽奖系统 > 注册]");
			// 调用注册方法
			Register.userRegister();
			break;
		case "2":
			System.out.println("[抽奖系统 > 登录]");
			// 调用登录方法
			// 记录(保存)登录的状态
			User.isLogin = Login.userLogin();
			break;
		case "3":
			System.out.println("[抽奖系统 > 抽奖]");
			// 调用抽奖方法
//			CJ.userCJ();
			break;
		default:
			System.out.println("输入有误,请重新输入!");
			Scanner scanner = new Scanner(System.in);
			menuNum = scanner.nextLine();
			break;
		}
	}
	
}