/**
 * Project Name:algorithm
 * File Name:ChineseSpliter.java
 * Package Name:com.zongtui.algorithm.classifier.bayes
 * Date:2015-4-14上午10:18:33
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier.bayes;

/**
 * ClassName: ChineseSpliter <br/>
 * Function: 中文分词器. <br/>
 * date: 2015-4-14 上午10:18:33 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */

import java.io.IOException;

import jeasy.analysis.MMAnalyzer;

public class ChineseSpliter {
	/**
	 * 
	 * 对给定的文本进行中文分词
	 * 
	 * @param text
	 *            给定的文本
	 * 
	 * @param splitToken
	 *            用于分割的标记,如"|"
	 * 
	 * @return 分词完毕的文本
	 */

	public static String split(String text, String splitToken) {
		String result = null;
		MMAnalyzer analyzer = new MMAnalyzer();
		try {
			result = analyzer.segment(text, splitToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
