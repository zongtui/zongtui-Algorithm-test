/**
 * Project Name:algorithm
 * File Name:ConsoleDemo.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45
 * Date:2015-3-30下午5:43:55
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;

import com.zongtui.algorithm.decisiontree.c45.decision.DecisionTree;

/**
 * ClassName: decisiontreeDemo <br/>
 * Function: 以不同方式输出 <br/>
 * date: 2015-3-30 下午5:43:55 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class decisiontreeDemo {
	@Test
	public void testConsole() {
		ArrayList<String []>  array= new ArrayList<String[]> ();
		InputStream is;
		BufferedReader bufferReader;
		String temps;
		int countDataIndex=0;//计算列数
		int countLength=0;//计算行号
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/data.txt");  
			bufferReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while((temps=bufferReader.readLine())!=null){
				String[] tempArray=temps.split(",|，");
				if(countLength==0){
					countLength++;
					countDataIndex=tempArray.length;
					array.add(tempArray);
				}else if(tempArray.length==countDataIndex){
					countLength++;
					array.add(tempArray);
				}else{
					countLength++;
					System.err.println("警告：\n第"+countLength+"行的数据有漏缺，请补全数据再尝试！");
					System.exit(-1);
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DecisionTree decisionTree = new DecisionTree();
		decisionTree.create(array.toArray(), 4);
		System.out.println("--------------------C4.5決策樹--------------------");
		System.out.println(decisionTree.getResult_buffer().toString());
		System.out.println("----------------------執行過程----------------------");
		System.out.println(decisionTree.getProcess_buffer().toString());
	}
}

