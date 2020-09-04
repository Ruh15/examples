package com.rh.examples.demos.test;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestHashMap {
	public static void main(String args[]) {
//		writer();
//		reader();
		Teacher teacher = new Teacher("老师好");
		teacher.put("1", "sss");
		System.out.println(teacher);
		System.out.println(JSON.toJSONString(teacher));

	}
	public static void writer(){
		Map<Integer,Student> students=new HashMap<Integer,Student>();
		students.put(1, new Student("A"));
		students.put(2, new Student("B"));
		File stuInfo = new File("E://stuInfo.txt");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream( new FileOutputStream(stuInfo));
			oos.writeObject(students);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void reader(){
		Map<Integer,Student> students= null;
		File stuInfo = new File("E://stuInfo.txt");
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(stuInfo));
			Object o = ois.readObject();
			students = (Map<Integer,Student>)o;
			
			System.out.println(students);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class Student implements Serializable{
	Student(String name){
		this.name = name;
	}
	private String name;
	
	@Override
	public String toString(){
		
		return "Student name:"+name;
	}
}

class Teacher extends HashMap implements Serializable {
	private String name;
	Teacher(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher{" +
				"name='" + name + '\'' +
				'}';
	}
}
