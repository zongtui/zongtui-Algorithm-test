/**
 * Project Name:algorithm
 * File Name:TestCreateFile.java
 * Package Name:com.zongtui.algorithm.classifier
 * Date:2015-4-14下午5:56:50
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier;

import java.io.File;

/**
 * ClassName: TestCreateFile <br/>
 * Function: 测试创建文件 <br/>
 * date: 2015-4-14 下午5:56:50 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class TestCreateFile {

	/**
	 * main:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author feng
	 * @param args
	 * @since JDK 1.7
	 */
	public static void main(String[] args) {
		for (int i = 1; i <= 4200; i++) {
			String filePath = "D:\\TrainningSet\\C000002\\"+i+".txt";
			File fileName = new File(filePath);
//			System.out.println(filePath);
			try {
				createFile(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}

