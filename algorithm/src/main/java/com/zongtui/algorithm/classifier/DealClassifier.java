/**
 * Project Name:algorithm
 * File Name:DealClassifier.java
 * Package Name:com.zongtui.algorithm.classifier
 * Date:2015-4-14下午5:38:00
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName: DealClassifier <br/>
 * Function: 处理分类问题. <br/>
 * date: 2015-4-14 下午5:38:00 <br/>
 * 
 * @author feng
 * @version
 * @since JDK 1.7
 */
public class DealClassifier {

	/**
	 * main:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author feng
	 * @param args
	 * @since JDK 1.7
	 */
	public static void main(String[] args) {
		DealClassifier dcf = new DealClassifier();
		try {
			dcf.buildClassifierData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * buildClassifierData:处理分类数据. <br/>
	 * 
	 * @author feng
	 * @throws SQLException
	 * @since JDK 1.7
	 */
	public void buildClassifierData() throws SQLException {
		try {
			// 加载MySql的驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://192.168.0.7:3306/ucenterdb", "stat",
				"s@t1k7akdt^ota");

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT info FROM userbasic WHERE info IS NOT NULL LIMIT 0,4200");

		int i = 0;
		while (rs.next()) {
			String content = rs.getString("info");
			i++;
			String filePath = "D:\\TrainningSet\\C000002\\" + i + ".txt";
			File fileName = new File(filePath);
			try {
				writeTxtFile(content, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		rs.close();
		stmt.close();
		conn.close();
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
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

	/**
	 * writeTxtFile:向文件中写入内容. <br/>
	 * 
	 * @author feng
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static boolean writeTxtFile(String content, File fileName)
			throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GBK"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

}
