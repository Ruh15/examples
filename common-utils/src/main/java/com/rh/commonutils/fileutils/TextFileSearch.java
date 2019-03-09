package com.rh.commonutils.fileutils;

import java.io.*;

/**
 * 对文本文件的关键词进行搜索
 * @author Abel
 *
 */
public class TextFileSearch {

	public static void main(String[] args) {
		TextFileSearch search = new TextFileSearch();
		InputStream stream = search.getClass().getResourceAsStream("1.txt");

		search.SearchKeywordAndPrint(new File("G:\\ruhui\\swagger\\src\\main\\resources\\1.txt"), "insBankMobile");
	}

	public void SearchKeyword(File file,String keyword) {
		//参数校验
		verifyParam(file, keyword);
		
		//行读取
		LineNumberReader lineReader = null;
		try {
			lineReader = new LineNumberReader(new FileReader(file));
			String readLine = null;
			while((readLine =lineReader.readLine()) != null){
				//判断每一行中,出现关键词的次数
				int index = 0;
				int next = 0;
				int times = 0;//出现的次数
				//判断次数
				while((index = readLine.indexOf(keyword,next)) != -1) {
					next = index + keyword.length();
					times++;
				}
				if(times > 0) {
					System.out.println("第"+ lineReader.getLineNumber() +"行" + "出现 "+keyword+" 次数: "+times);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭流
			close(lineReader);
		}
	}

	public void SearchKeywordAndPrint(File file,String keyword) {
		//行读取
		LineNumberReader lineReader = null;
		try {
			lineReader = new LineNumberReader(new FileReader(file));
			String readLine = null;
				int count = 0;
			while((readLine =lineReader.readLine()) != null){
				//判断每一行中,出现关键词的次数
				int index = 0;
				int next = 0;
				//判断次数
				while((index = readLine.indexOf("contractNo",next)) != -1) {
					next = index + keyword.length();
					System.out.print(readLine.substring(index, index + 29));
					count ++;
				}
				while((index = readLine.indexOf(keyword,next)) != -1) {
					next = index + keyword.length();
					System.out.print(readLine.substring(index, index + 28));
				}
				System.out.println();
			}
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭流
			close(lineReader);
		}
	}


	/**
	 * 参数校验
	 * 
	 * <br>
	 * Date: 2014年11月5日
	 */
	private void verifyParam(File file, String keyword) {
		//对参数进行校验证
		if(file == null ){
			throw new NullPointerException("the file is null");
		}
		if(keyword == null || keyword.trim().equals("")){
			throw new NullPointerException("the keyword is null or \"\" ");
		}
		
		if(!file.exists()) {
			throw new RuntimeException("the file is not exists");
		}
		//非目录
		if(file.isDirectory()){
			throw new RuntimeException("the file is a directory,not a file");
		}
		
		//可读取
		if(!file.canRead()) {
			throw new RuntimeException("the file can't read");
		}
	}
	
	/**
	 * 关闭流
	 * <br>
	 * Date: 2014年11月5日
	 */
	private void close(Closeable able){
		if(able != null){
			try {
				able.close();
			} catch (IOException e) {
				e.printStackTrace();
				able = null;
			}
		}
	}
 
}
